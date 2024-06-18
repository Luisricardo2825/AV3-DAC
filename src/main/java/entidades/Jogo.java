package entidades;

import java.util.Date;

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
import javax.persistence.Transient;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table
@NamedQueries({ @NamedQuery(name = "buscaTime", query = "SELECT a FROM Jogo a where time1= :time OR time2= :time") })
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
	Time time1;

	@Column
	@Enumerated(EnumType.STRING)
	Time time2;

	@ManyToOne
	Campeonato campeonato;

	@Column
	Integer golsTime1;

	@Column
	Integer golsTime2;

	@Transient
	Integer idCampeonato = campeonato != null ? campeonato.getId() : null;

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

	public Time getTime1() {
		return time1;
	}

	public void setTime1(Time time1) {
		this.time1 = time1;
	}

	public Time getTime2() {
		return time2;
	}

	public void setTime2(Time time2) {
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

	public Time getVencedor() {
		if (golsTime1 > golsTime2) {
			return time1;
		} else if (golsTime2 > golsTime1) {
			return time2;
		} else {
			return null;
		}
	}

	public boolean isEmpate() {
		return golsTime1.equals(golsTime2);
	}

}
