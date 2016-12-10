package org.odddev.fantlab.award;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author kenrube
 * @since 07.12.16
 */

public class Award {

    @SerializedName("award_close")
    @Expose
    private String awardClose;

    @SerializedName("award_id")
    @Expose
    private String awardId;

    @SerializedName("award_type")
    @Expose
    private String awardType;

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("compiler")
    @Expose
    private String compiler;

    @SerializedName("contests")
    @Expose
    private List<Contest> contests;

    @SerializedName("copyright")
    @Expose
    private String copyright;

    @SerializedName("copyright_link")
    @Expose
    private String copyrightLink;

    @SerializedName("country_id")
    @Expose
    private String countryId;

    @SerializedName("country_name")
    @Expose
    private String countryName;

    @SerializedName("curator_id")
    @Expose
    private String curatorId;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("homepage")
    @Expose
    private String homepage;

    @SerializedName("is_opened")
    @Expose
    private Integer isOpened;

    @SerializedName("lang_id")
    @Expose
    private String langId;

    @SerializedName("max_date")
    @Expose
    private String maxDate;

    @SerializedName("min_date")
    @Expose
    private String minDate;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("non_fantastic")
    @Expose
    private String nonFantastic;

    @SerializedName("notes")
    @Expose
    private String notes;

    @SerializedName("process_status")
    @Expose
    private String processStatus;

    @SerializedName("rusname")
    @Expose
    private String rusname;

    @SerializedName("show_in_list")
    @Expose
    private String showInList;

    public String getAwardId() {
        return awardId;
    }

    public String getDescription() {
        return description;
    }

    public String getMaxDate() {
        return maxDate;
    }

    public String getMinDate() {
        return minDate;
    }

    public String getName() {
        return name;
    }

    public String getRusname() {
        return rusname;
    }
}