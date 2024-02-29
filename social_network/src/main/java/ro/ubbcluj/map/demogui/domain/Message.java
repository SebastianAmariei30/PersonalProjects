package ro.ubbcluj.map.demogui.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Message extends Entity<Long>{
    Long from;
    List<Long> to;
    String message;
    LocalDateTime data;
    Long reply;

    public Message(Long from, List<Long> to, String message, LocalDateTime data) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.data = data;
        this.reply=null;
    }

    @Override
    public String toString() {
        return "Message{" +
                "from=" + from +
                ", to=" + to +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", reply=" + reply +
                ", id=" + id +
                '}';
    }

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public List<Long> getTo() {
        return to;
    }

    public void setTo(List<Long> to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Long getReply() {
        return reply;
    }

    public void setReply(Long reply) {
        this.reply=reply;
    }
}
