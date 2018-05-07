package com.example.demo.controller;

import com.example.demo.models.Classroom;
import com.example.demo.models.Emessage;
import com.example.demo.models.Student;
import com.example.demo.repository.ClassroomRepository;
import com.example.demo.repository.EmessageRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.services.GenerateKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.Optional;
import com.example.demo.services.Hidey;

@Controller
public class HomeController {

    @Autowired
    ClassroomRepository classroomRepository;

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    EmessageRepository emessageRepository;

    @RequestMapping("/generateKeys/{courseid}")
    public String generateCourseKeys(@PathVariable("courseid") Long id, Model model) {
        Classroom classroom = classroomRepository.findById(id).get();
        Iterable<Student> students = studentRepository.findByClassroomid(id);

        GenerateKeys gk;

        for (Student student : students) {
            try {
                gk = new GenerateKeys(1024);
                gk.createKeys();
                student.setPrivateKey(gk.getPrivateKey().getEncoded());
                student.setPublicKey(gk.getPublicKey().getEncoded());
                studentRepository.save(student);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        students = studentRepository.findByClassroomid(id);
        model.addAttribute("classroom", classroom);
        model.addAttribute("students", students);
        return "confirmclass";
    }

    @RequestMapping("/createclassroom/{teacherName}/{courseName}/{courseid}/{passcode}")
    public String keysDone(@PathVariable("teacherName") String teacher,@PathVariable("courseName") String course,@PathVariable("courseid") String cid,@PathVariable("passcode") String passcode, Model model){
        Classroom classroom = new Classroom(cid, passcode, "", teacher, course, 1L);
        classroomRepository.save(classroom);
        model.addAttribute("classroom", classroom);
        return "confirm";
    }

    @RequestMapping("/addstudent/{courseid}/{name}/{pic}")
    public String addStudent(@PathVariable("courseid") Long cid, @PathVariable("name") String name, @PathVariable("pic") String pic, Model model){

        Student student = new Student(cid, pic, name);
        studentRepository.save(student);
        Classroom classroom = classroomRepository.findById(cid).get();
        Iterable<Student> students = studentRepository.findByClassroomid(cid);
        model.addAttribute("classroom", classroom);
        model.addAttribute("students", students);

        return "confirmclass";
    }

    @RequestMapping("/messages/{courseid}")
    public String showMessages(@PathVariable("courseid") Long id, Model model){
        Classroom classroom = classroomRepository.findById(id).get();
        Iterable<Emessage> messages = emessageRepository.findAll();
        model.addAttribute("messages", messages);
        model.addAttribute("classroom", classroom);
        return "messages";
    }

    @RequestMapping("/sendmessage/{courseid}")
    public String sendMessage(@PathVariable("courseid") Long id, Model model){
        Iterable<Student> students = studentRepository.findByClassroomid(id);
        model.addAttribute("courseid", id);
        model.addAttribute("students", students);
        return "createmessage";
    }

    @RequestMapping("/processmessage")
    public String processNewMessage(@RequestParam("courseid") Long cid,
                                    @RequestParam("fromperson") Long fstudent,
                                    @RequestParam("toperson") Long tstudent,
                                    @RequestParam("message") String message,
                                    @RequestParam(value = "beEncrypted", required = false) String encrypted,
                                    @RequestParam(value = "beSigned", required = false) String signed,
                                    Model model){


        Long encrypt = 0L;
        Long digitallysigned = 0L;
        Student senderPerson = studentRepository.findById(fstudent).get();
        Student receivePerson = studentRepository.findById(tstudent).get();

        String privString = senderPerson.getPrivateKey().toString();
        String pubString = receivePerson.getPublicKey().toString();
        String signature = "";
        PrivateKey privKey;
        PublicKey pubKey;
        try {
            privKey = Hidey.getPrivateKeyFromString(privString);
            pubKey = Hidey.getPublicKeyFromString(pubString);

            if(signed != null)
            {
                digitallysigned = 1L;
                signature = Hidey.sign("verified", Hidey.getPrivateKeyFromString(senderPerson.getPrivateKey().toString()));

            }
            if(encrypted != null)
            {
                encrypt = 1L;
                String encryptedMessage = Hidey.encrypt(message, pubKey );
                message = encryptedMessage;
            }

            // temp line of code
            signature = Hidey.decrypt(message, Hidey.getPrivateKeyFromString(receivePerson.getPrivateKey().toString()));
        } catch (Exception e)
        {
            signature = e.getMessage().toString();
            signature = signature +  "   ";
        }


        Emessage emessage = new Emessage(fstudent,
                tstudent,
                "No Subject",
                message,
                signature,
                encrypt == 1L,
                digitallysigned == 1L,
                new Date());

        emessageRepository.save(emessage);
        return "redirect:/messages/"+cid.toString();
    }
}
