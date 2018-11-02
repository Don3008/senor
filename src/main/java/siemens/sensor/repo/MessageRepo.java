package siemens.sensor.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import siemens.sensor.domain.Message;

public interface MessageRepo extends JpaRepository<Message, Long> {

}
