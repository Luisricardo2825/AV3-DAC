package entidades;

import java.util.Date;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;


@NamedQueries({
	@NamedQuery(
	name = "maxNumeroSorteado",
	query = "SELECT max(a.numeroSorteado) FROM Jogo a"
	)
})
@Entity
@Table
public class Jogo {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	Date dataCadastro = new Date();

	@Column
	Integer numeroSorteado = (new Random()).nextInt(10 - 1) + 1;

	@Column
	Integer v1;

	@Column
	Integer v2;

	@Column
	Integer v3;

	@Column
	Integer v4;

	@Column
	Integer v5;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Integer getNumeroSorteado() {
		return numeroSorteado;
	}

	public void setNumeroSorteado(Integer numeroSorteado) {
		this.numeroSorteado = numeroSorteado;
	}

	public Integer getV1() {
		return v1;
	}

	public void setV1(Integer v1) {
		this.v1 = v1;
	}

	public Integer getV2() {
		return v2;
	}

	public void setV2(Integer v2) {
		this.v2 = v2;
	}

	public Integer getV3() {
		return v3;
	}

	public void setV3(Integer v3) {
		this.v3 = v3;
	}

	public Integer getV4() {
		return v4;
	}

	public void setV4(Integer v4) {
		this.v4 = v4;
	}

	public Integer getV5() {
		return v5;
	}

	public void setV5(Integer v5) {
		this.v5 = v5;
	}

	public String getValues() {

		return v1 + "," + v2 + "," + v3 + "," + v4 + "," + v5;
	}

	public Integer getGreaterBetweenValues() {

		Integer greater = v1;

		if (greater < v2)
			greater = v2;

		if (greater < v3)
			greater = v3;

		if (greater < v4)
			greater = v4;

		if (greater < v5)
			greater = v5;

		return greater;
	}

	public boolean getSorteadoIsInValues() {

		if (numeroSorteado == v1 || numeroSorteado == v2 || numeroSorteado == v3 || numeroSorteado == v4
				|| numeroSorteado == v5)
			return true;

		return false;

	}

	/**
	 * Checa se os valores estao entre 1 e 10
	 *
	 * @return true se estiverem entre 1 e 10, false caso contrario
	 */
	public boolean checkValues() {

		if (v1 > 10 || v1 < 1 || v2 > 10 || v2 < 1 || v3 > 10 || v3 < 1 || v4 > 10 || v4 < 1 || v5 > 10 || v5 < 1)
			return false;

		return true;
	}

}
