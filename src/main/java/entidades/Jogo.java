package entidades;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table
public class Jogo {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	Date dataPartida = new Date();

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	Date dataCadastro = new Date();

	@Column
	@Enumerated(EnumType.STRING)
	Times time1;

	@Column
	@Enumerated(EnumType.STRING)
	Times time2;

	@ManyToOne
	Campeonato campeonato;

	@Column
	Integer golsTime1;

	@Column
	Integer golsTime2;
	
	Integer idCampeonato;

	public Integer getIdCampeonato() {
		return idCampeonato;
	}

	public void setIdCampeonato(Integer idCampeonato) {
		this.idCampeonato = idCampeonato;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataPartida() {
		return dataPartida;
	}

	public void setDataPartida(Date dataPartida) {
		this.dataPartida = dataPartida;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Times getTime1() {
		return time1;
	}

	public void setTime1(Times time1) {
		this.time1 = time1;
	}

	public Times getTime2() {
		return time2;
	}

	public void setTime2(Times time2) {
		this.time2 = time2;
	}

	public Campeonato getCampeonato() {
		return campeonato;
	}

	public void setCampeonato(Campeonato campeonato) {
		this.campeonato = campeonato;
	}

	public Integer getGolsTime1() {
		return golsTime1;
	}

	public void setGolsTime1(Integer golsTime1) {
		this.golsTime1 = golsTime1;
	}

	public Integer getGolsTime2() {
		return golsTime2;
	}

	public void setGolsTime2(Integer golsTime2) {
		this.golsTime2 = golsTime2;
	}

	public enum Times {
		A,
		B,
		C;

		public boolean equals(Times time) {
			return this == time;
		}

		public List<String> getTimes() {
			return List.of(A.toString(), B.toString(), C.toString());
		}
	}

}
