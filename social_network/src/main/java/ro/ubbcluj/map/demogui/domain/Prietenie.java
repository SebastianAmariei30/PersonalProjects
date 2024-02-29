package ro.ubbcluj.map.demogui.domain;

import java.time.LocalDateTime;


public class Prietenie extends Entity<Tuple<Long,Long>> {

    LocalDateTime date;

    String status;

    public Prietenie(Long userId1, Long userId2, LocalDateTime date,String status) {
        this.setId(new Tuple<>(userId1, userId2));
        this.date = date;
        this.status=status;
    }

    /**
     *
     * @return the date when the friendship was created
     */
    public LocalDateTime getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Prietenie{" +
                "id=" + getId() +
                ", date=" + date +
                '}';
    }
}
