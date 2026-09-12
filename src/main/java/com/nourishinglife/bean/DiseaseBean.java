package com.nourishinglife.bean;

import com.nourishinglife.data.Disease;
import com.nourishinglife.data.DiseaseData;
import com.nourishinglife.data.Meal;
import com.nourishinglife.data.MealCategory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "diseaseBean")
@ViewScoped
public class DiseaseBean implements Serializable {

    private String diseaseId;
    private Disease disease;
    private MealCategory selectedCategory = MealCategory.BREAKFAST;
    private Meal selectedMeal;

    public void init() {
        if (diseaseId == null) {
            diseaseId = FacesContext.getCurrentInstance()
                    .getExternalContext().getRequestParameterMap().get("diseaseId");
        }
        if (diseaseId != null) {
            disease = DiseaseData.getDiseaseById(diseaseId);
        }
    }

    public List<Disease> getDiseases() {
        return DiseaseData.getDiseases();
    }

    public List<MealCategory> getCategories() {
        return java.util.Arrays.asList(MealCategory.values());
    }

    public List<Meal> getMealsForSelectedCategory() {
        if (disease == null) {
            return java.util.Collections.emptyList();
        }
        return DiseaseData.getMealsFor(disease.getId(), selectedCategory);
    }

    public void selectCategory(MealCategory category) {
        this.selectedCategory = category;
        this.selectedMeal = null;
    }

    public void selectMeal(Meal meal) {
        this.selectedMeal = meal;
    }

    public void closeMealDetail() {
        this.selectedMeal = null;
    }

    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    public Disease getDisease() {
        return disease;
    }

    public MealCategory getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(MealCategory selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public Meal getSelectedMeal() {
        return selectedMeal;
    }
}
