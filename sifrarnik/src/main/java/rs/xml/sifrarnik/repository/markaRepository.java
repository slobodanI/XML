package rs.xml.sifrarnik.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.xml.sifrarnik.model.Marka;

public interface markaRepository extends JpaRepository<Marka, Long>
{

}