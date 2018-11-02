package siemens.sensor.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import siemens.sensor.domain.Message;
import siemens.sensor.repo.MessageRepo;

import java.util.List;

@RestController
@RequestMapping("message") //все обращения по адресу, нач. со слова message будут перенаправляться на этот контр-р
public class MessageController {
//    private int counter = 4;
//
//    private List<Map<String, String>> messages = new ArrayList<Map<String, String>>() {{
//        add(new HashMap<String, String>() {{
//            put("id", "1");
//            put("temperature", "25.5 С");
//            put("coordinates", "132.256, 44.687");
//        }});
//        add(new HashMap<String, String>() {{
//            put("id", "2");
//            put("temperature", "21.3 С");
//            put("coordinates", "67.112, 46.091");
//        }});
//        add(new HashMap<String, String>() {{
//            put("id", "3");
//            put("temperature", "17.0 С");
//            put("coordinates", "102.003, 67.101");
//        }});
//    }};
    @Autowired
    private final MessageRepo messageRepo;

    public MessageController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping
    public List<Message> list() {
        return messageRepo.findAll();
    }

    @GetMapping("{id}")
    public Message getOne(@PathVariable("id") Message message) {
        return message;
    }

    @PostMapping
    public Message create(@RequestBody Message message) {
        return messageRepo.save(message);
    }

    @PutMapping("{id}")
    public Message update(
            @PathVariable("id") Message messageFromDb,
            @RequestBody Message message) {
        BeanUtils.copyProperties(message, messageFromDb, "id");
        return messageRepo.save(messageFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message) {
        messageRepo.delete(message);
    }
}
