package tenkacheva.work.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ContactRestController {

    private final ContactDao contactDao;

    @Autowired
    public ContactRestController(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    @GetMapping({"/contact/all", "/", ""})
    public String getAllContacts(Model model) {
        model.addAttribute("contacts", contactDao.selectAll());
        return "index";
    }

    @GetMapping("/contact/add")
    public String addContact(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String message,
            Model model) {
        Contact contact = id == null ? null : contactDao.select(id);
        model.addAttribute("contact", contact);
        model.addAttribute("message", message);
        return "add-contact";
    }

    @PostMapping({"/contact/add", "/contact/add/"})
    public RedirectView addContact(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String phone) {

        Contact contact;
        try {
            contact = new Contact(0, firstName, lastName, email, phone);
        } catch (IllegalArgumentException exception) {
            return new RedirectView("/contact/add?message=" + exception.getMessage());
        }

        boolean isOperationSuccess = contactDao.insert(contact);
        if (isOperationSuccess) {
            return new RedirectView("/contact/all");
        }

        return new RedirectView("/contact/add?message=error occurred");
    }

    @PostMapping("/contact/add/{id}")
    public RedirectView editContact(
            @PathVariable long id,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String phone) {
        Contact contact;
        try {
            contact = new Contact(id, firstName, lastName, email, phone);
        } catch (IllegalArgumentException exception) {
            return new RedirectView("/contact/add?id=" + id + "&message=" + exception.getMessage());
        }

        boolean isOperationSuccess = contactDao.update(contact);
        if (isOperationSuccess) {
            return new RedirectView("/contact/all");
        }

        return new RedirectView("/contact/add?id=" + id + "&message=error occurred");
    }

    @PostMapping("/contact/delete/{id}")
    public RedirectView deleteContact(@PathVariable long id) {
        boolean isOperationSuccess = contactDao.delete(id);
        if (isOperationSuccess) {
            return new RedirectView("/contact/all");
        }

        return new RedirectView("/contact/all?error=error occurred");
    }
}
