package nl.fourscouts.blog.replays.repositories;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import nl.fourscouts.blog.replays.domain.Status;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@ToString
@Table(name = "orderhistory")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderHistoryItem {
	@Id
	@GeneratedValue
	private Long id;

	@Getter
	private String reference;

	@Getter
	private String customerId;

	@Getter
	private Status status;

	@Getter
	private LocalDateTime timePlaced;

	public OrderHistoryItem(String reference, String customerId, LocalDateTime timePlaced) {
		this.reference = reference;
		this.customerId = customerId;

		status = Status.PLACED;

		this.timePlaced = timePlaced;
	}
}
