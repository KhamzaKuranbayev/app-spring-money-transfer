package uz.pdp.appspringmoneyconvertor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appspringmoneyconvertor.entity.Operation;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Integer> {
}
