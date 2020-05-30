package rs.xml.sifrarnik.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.xml.sifrarnik.model.Model;

public interface modelRepository extends JpaRepository<Model, Long>
{

}
