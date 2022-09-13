package com.vaxreact;

import javafx.scene.control.TextArea;

public class DocAdverseReactionTableModel {
    String adverseReaction;
    Integer gravityLevel;
    String description;

    public DocAdverseReactionTableModel(String adverseReaction,Integer gravityLevel, String description) {
        this.adverseReaction = adverseReaction;
        this.gravityLevel = gravityLevel;
        this.description = description;
    }


    public String getAdverseReaction() {
        return adverseReaction;
    }

    public Integer getGravityLevel() {
        return gravityLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setAdverseReaction(String adverseReaction) {
        this.adverseReaction = adverseReaction;
    }

    public void setGravityLevel(Integer gravityLevel) {
        this.gravityLevel = gravityLevel;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
