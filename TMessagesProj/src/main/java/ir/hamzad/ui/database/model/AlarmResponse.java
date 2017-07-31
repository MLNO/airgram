package org.telegram.ui.database.model;

public class AlarmResponse {
    private Integer displayCount;
    private Boolean exitOnDismiss;
    private Long id;
    private String imageUrl;
    private String message;
    private String negativeBtnAction;
    private String negativeBtnText;
    private String negativeBtnUrl;
    private String positiveBtnAction;
    private String positiveBtnText;
    private String positiveBtnUrl;
    private Integer showCount;
    private Integer targetNetwork;
    private Integer targetVersion;
    private String title;

    public AlarmResponse(Long id, String title, String message, String imageUrl, String positiveBtnText, String positiveBtnAction, String positiveBtnUrl, String negativeBtnText, String negativeBtnAction, String negativeBtnUrl, Integer showCount, Boolean exitOnDismiss, Integer targetNetwork, Integer displayCount, Integer targetVersion) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.imageUrl = imageUrl;
        this.positiveBtnText = positiveBtnText;
        this.positiveBtnAction = positiveBtnAction;
        this.positiveBtnUrl = positiveBtnUrl;
        this.negativeBtnText = negativeBtnText;
        this.negativeBtnAction = negativeBtnAction;
        this.negativeBtnUrl = negativeBtnUrl;
        this.showCount = showCount;
        this.exitOnDismiss = exitOnDismiss;
        this.targetNetwork = targetNetwork;
        this.displayCount = displayCount;
        this.targetVersion = targetVersion;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPositiveBtnText() {
        return this.positiveBtnText;
    }

    public void setPositiveBtnText(String positiveBtnText) {
        this.positiveBtnText = positiveBtnText;
    }

    public String getPositiveBtnAction() {
        return this.positiveBtnAction;
    }

    public void setPositiveBtnAction(String positiveBtnAction) {
        this.positiveBtnAction = positiveBtnAction;
    }

    public String getPositiveBtnUrl() {
        return this.positiveBtnUrl;
    }

    public void setPositiveBtnUrl(String positiveBtnUrl) {
        this.positiveBtnUrl = positiveBtnUrl;
    }

    public String getNegativeBtnText() {
        return this.negativeBtnText;
    }

    public void setNegativeBtnText(String negativeBtnText) {
        this.negativeBtnText = negativeBtnText;
    }

    public String getNegativeBtnAction() {
        return this.negativeBtnAction;
    }

    public void setNegativeBtnAction(String negativeBtnAction) {
        this.negativeBtnAction = negativeBtnAction;
    }

    public String getNegativeBtnUrl() {
        return this.negativeBtnUrl;
    }

    public void setNegativeBtnUrl(String negativeBtnUrl) {
        this.negativeBtnUrl = negativeBtnUrl;
    }

    public Integer getShowCount() {
        return this.showCount;
    }

    public void setShowCount(Integer showCount) {
        this.showCount = showCount;
    }

    public Boolean getExitOnDismiss() {
        return this.exitOnDismiss;
    }

    public void setExitOnDismiss(Boolean exitOnDismiss) {
        this.exitOnDismiss = exitOnDismiss;
    }

    public Integer getTargetNetwork() {
        return this.targetNetwork;
    }

    public void setTargetNetwork(Integer targetNetwork) {
        this.targetNetwork = targetNetwork;
    }

    public Integer getDisplayCount() {
        return this.displayCount;
    }

    public void setDisplayCount(Integer displayCount) {
        this.displayCount = displayCount;
    }

    public Integer getTargetVersion() {
        return this.targetVersion;
    }

    public void setTargetVersion(Integer targetVersion) {
        this.targetVersion = targetVersion;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
