package IGT.Flight;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BookingId implements Serializable {

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "flight_id")
    private Long flightId;

    private BookingId() { }

    public BookingId(Long customerId, Long flightId) {
        this.customerId = customerId;
        this.flightId = flightId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        BookingId that = (BookingId) o;
        return Objects.equals(customerId, that.customerId) &&
                Objects.equals(flightId, that.flightId);
    }

}
