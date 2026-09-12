package com.nourishinglife.bean;

import com.nourishinglife.dao.FaqDAO;
import com.nourishinglife.dao.FeedbackDAO;
import com.nourishinglife.model.Faq;
import com.nourishinglife.model.Feedback;
import com.nourishinglife.model.FeedbackStatus;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@ManagedBean(name = "feedbackBean")
@ViewScoped
public class FeedbackBean implements Serializable {

    @ManagedProperty("#{authBean}")
    private AuthBean authBean;

    private final FeedbackDAO feedbackDAO = new FeedbackDAO();
    private final FaqDAO faqDAO = new FaqDAO();

    // --- Rating / create form (regular user) ---
    private int rating;

    @Size(max = 1000, message = "Message must be under 1000 characters")
    private String message;
    private boolean submitted;

    // --- Admin list / CRUD ---
    private List<Feedback> allFeedback;
    private Feedback editingFeedback;
    private Feedback replyTarget;

    public void init() {
        if (authBean != null && authBean.isAdmin() && allFeedback == null) {
            loadAll();
        }
    }

    public void loadAll() {
        allFeedback = feedbackDAO.findAll();
    }

    public String submitFeedback() {
        if (rating < 1 || rating > 5) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Choose a rating", "Please select 1 to 5 stars."));
            return null;
        }
        Feedback feedback = new Feedback();
        feedback.setUser(authBean.getCurrentUser());
        feedback.setRating(rating);
        feedback.setMessage(message == null || message.trim().isEmpty() ? "No additional comments" : message.trim());
        feedback.setStatus(FeedbackStatus.PENDING);
        feedbackDAO.create(feedback);

        submitted = true;
        rating = 0;
        message = null;
        return null;
    }

    public void startReply(Feedback feedback) {
        this.replyTarget = feedback;
    }

    public void cancelReply() {
        replyTarget = null;
    }

    /** Update: admin replies to a pending feedback; also auto-creates an FAQ entry. */
    public void reply() {
        if (replyTarget == null) {
            return;
        }
        String reply = replyTarget.getDraftReply();
        if (reply == null || reply.trim().isEmpty()) {
            return;
        }
        replyTarget.setAdminReply(reply.trim());
        replyTarget.setStatus(FeedbackStatus.REPLIED);
        replyTarget.setRepliedAt(LocalDateTime.now());
        feedbackDAO.update(replyTarget);

        faqDAO.create(new Faq(replyTarget.getMessage(), reply.trim()));

        replyTarget = null;
        loadAll();
    }

    public void startEdit(Feedback feedback) {
        this.editingFeedback = feedback;
    }

    /** Update: admin edits a feedback record directly (message/rating). */
    public void saveEdit() {
        if (editingFeedback != null) {
            feedbackDAO.update(editingFeedback);
            editingFeedback = null;
            loadAll();
        }
    }

    public void cancelEdit() {
        editingFeedback = null;
    }

    public void delete(Long id) {
        feedbackDAO.delete(id);
        loadAll();
    }

    public AuthBean getAuthBean() {
        return authBean;
    }

    public void setAuthBean(AuthBean authBean) {
        this.authBean = authBean;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public List<Feedback> getAllFeedback() {
        return allFeedback;
    }

    public Feedback getEditingFeedback() {
        return editingFeedback;
    }

    public void setEditingFeedback(Feedback editingFeedback) {
        this.editingFeedback = editingFeedback;
    }

    public Feedback getReplyTarget() {
        return replyTarget;
    }
}
