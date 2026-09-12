package com.nourishinglife.bean;

import com.nourishinglife.dao.FaqDAO;
import com.nourishinglife.model.Faq;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "faqBean")
@ViewScoped
public class FaqBean implements Serializable {

    private final FaqDAO faqDAO = new FaqDAO();
    private List<Faq> faqs;
    private Long openId;

    public void init() {
        if (faqs == null) {
            faqs = faqDAO.findAll();
        }
    }

    public void toggle(Long id) {
        openId = id.equals(openId) ? null : id;
    }

    public boolean isOpen(Long id) {
        return id.equals(openId);
    }

    public List<Faq> getFaqs() {
        return faqs;
    }
}
