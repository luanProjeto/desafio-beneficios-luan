package br.com.luan.beneficios.repo;

import br.com.luan.beneficios.domain.Beneficio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeneficioRepository extends JpaRepository<Beneficio, Long> { }
