package com.github.idelstak.storymap;

import java.util.Objects;

public class Node {

    private int id;
    private int yesId;
    private int noId;
    private String description;
    private String question;

    public Node(int id, int yesId, int noId, String description, String question) {
        this.id = id;
        this.yesId = yesId;
        this.noId = noId;
        this.description = description;
        this.question = question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYesId() {
        return yesId;
    }

    public void setYesId(int yesId) {
        this.yesId = yesId;
    }

    public int getNoId() {
        return noId;
    }

    public void setNoId(int noId) {
        this.noId = noId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.id;
        hash = 29 * hash + this.yesId;
        hash = 29 * hash + this.noId;
        hash = 29 * hash + Objects.hashCode(this.description);
        hash = 29 * hash + Objects.hashCode(this.question);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Node other = (Node) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.yesId != other.yesId) {
            return false;
        }
        if (this.noId != other.noId) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return Objects.equals(this.question, other.question);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Node{");
        sb.append("id=").append(id);
        sb.append(", yesId=").append(yesId);
        sb.append(", noId=").append(noId);
        sb.append(", description=").append(description);
        sb.append(", question=").append(question);
        sb.append('}');
        return sb.toString();
    }

}
