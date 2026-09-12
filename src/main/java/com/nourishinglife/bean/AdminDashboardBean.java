package com.nourishinglife.bean;

import com.nourishinglife.dao.FeedbackDAO;
import com.nourishinglife.dao.UserDAO;
import com.nourishinglife.data.DiseaseData;
import com.nourishinglife.model.Feedback;
import com.nourishinglife.model.FeedbackStatus;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Drives the admin dashboard's stats + the two pure-CSS charts (bar chart via
 * per-row inline "height:%" styles, pie chart via a single inline conic-gradient).
 */
@ManagedBean(name = "adminDashboardBean")
@ViewScoped
public class AdminDashboardBean implements Serializable {

    public static class BarItem {
        public final String label;
        public final long value;
        public final String color;
        public final int heightPercent;

        BarItem(String label, long value, String color, int heightPercent) {
            this.label = label;
            this.value = value;
            this.color = color;
            this.heightPercent = heightPercent;
        }

        public String getLabel() { return label; }
        public long getValue() { return value; }
        public String getColor() { return color; }
        public int getHeightPercent() { return heightPercent; }
    }

    private final UserDAO userDAO = new UserDAO();
    private final FeedbackDAO feedbackDAO = new FeedbackDAO();

    private long totalUsers;
    private long totalDiseases;
    private long totalMeals;
    private long totalFeedback;
    private long pendingFeedback;
    private long repliedFeedback;

    private List<BarItem> overviewBars;
    private String pieChartStyle;
    private List<Feedback> recentFeedback;

    public void init() {
        if (overviewBars != null) {
            return;
        }
        totalUsers = userDAO.countAll();
        totalDiseases = DiseaseData.getDiseases().size();
        totalMeals = DiseaseData.totalMealCount();
        totalFeedback = feedbackDAO.countAll();
        pendingFeedback = feedbackDAO.countByStatus(FeedbackStatus.PENDING);
        repliedFeedback = feedbackDAO.countByStatus(FeedbackStatus.REPLIED);

        long max = Math.max(1, Math.max(totalUsers, Math.max(totalDiseases, Math.max(totalMeals, totalFeedback))));
        overviewBars = new ArrayList<>();
        overviewBars.add(new BarItem("Users", totalUsers, "#10B981", pct(totalUsers, max)));
        overviewBars.add(new BarItem("Diseases", totalDiseases, "#3B82F6", pct(totalDiseases, max)));
        overviewBars.add(new BarItem("Meals", totalMeals, "#F59E0B", pct(totalMeals, max)));
        overviewBars.add(new BarItem("Feedback", totalFeedback, "#EF4444", pct(totalFeedback, max)));

        pieChartStyle = buildPieStyle(pendingFeedback, repliedFeedback);

        List<Feedback> all = feedbackDAO.findAll();
        recentFeedback = all.subList(0, Math.min(5, all.size()));
    }

    private int pct(long value, long max) {
        return (int) Math.round((value * 100.0) / max);
    }

    private String buildPieStyle(long pending, long replied) {
        long total = pending + replied;
        if (total == 0) {
            return "background:#E5E7EB";
        }
        double pendingDeg = (pending * 360.0) / total;
        return String.format(Locale.US,
                "background: conic-gradient(#F59E0B 0deg %.2fdeg, #10B981 %.2fdeg 360deg)",
                pendingDeg, pendingDeg);
    }

    public long getTotalUsers() { return totalUsers; }
    public long getTotalDiseases() { return totalDiseases; }
    public long getTotalMeals() { return totalMeals; }
    public long getTotalFeedback() { return totalFeedback; }
    public long getPendingFeedback() { return pendingFeedback; }
    public long getRepliedFeedback() { return repliedFeedback; }
    public List<BarItem> getOverviewBars() { return overviewBars; }
    public String getPieChartStyle() { return pieChartStyle; }
    public List<Feedback> getRecentFeedback() { return recentFeedback; }
}
