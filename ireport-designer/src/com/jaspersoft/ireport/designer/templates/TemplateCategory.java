/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jaspersoft.ireport.designer.templates;

/**
 *
 * @author gtoffoli
 */
public class TemplateCategory implements Comparable<TemplateCategory> {
    
    public static final String CATEGORY_ALL_REPORTS = "All_reports";
    public static final String CATEGORY_OTHER_REPORTS = "Other_reports";

    private String category = CATEGORY_OTHER_REPORTS;
    private String subCategory = "";

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category == null ? CATEGORY_OTHER_REPORTS : category;
    }

    /**
     * @return the subCategory
     */
    public String getSubCategory() {
        return subCategory;
    }

    /**
     * @param subCategory the subCategory to set
     */
    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory == null ? "" : subCategory;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof TemplateCategory)
        {
            TemplateCategory cat = (TemplateCategory)obj;
            if (cat.getCategory().equals(getCategory()) &&
                cat.getSubCategory().equals(getSubCategory()))
            {
                    return true;
            }
            return false;
        }

        return super.equals(obj);
    }

    public int compareTo(TemplateCategory o) {

        int c = getCategory().compareTo(getCategory());
        if (c == 0) return getSubCategory().compareTo(o.getSubCategory());
        return c;
    }



}
