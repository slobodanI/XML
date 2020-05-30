package rs.xml.sifrarnik.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.xml.sifrarnik.model.Mesto;

public interface mestoRepository extends JpaRepository<Mesto, Long>
{

}