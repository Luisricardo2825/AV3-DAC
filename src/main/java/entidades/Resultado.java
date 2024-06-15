package entidades;

public class Resultado {

	Time time;
	Integer vitorias;
	Integer pontuacao;
	Integer empates;
	Integer derrotas;
	Integer golsFeitos;
	Integer golsSofridos;
	Integer saldoDeGols;

	public Resultado(Time time, Integer vitorias, Integer empates, Integer derrotas, Integer pontuacao, Integer golsFeitos,
			Integer golsSofridos) {
		super();
		this.time = time;
		this.vitorias = vitorias;
		this.empates = empates;
		this.derrotas = derrotas;
		this.golsFeitos = golsFeitos;
		this.golsSofridos = golsSofridos;
		this.saldoDeGols = golsFeitos - golsSofridos;
		this.pontuacao = pontuacao;
	}

	public Integer getGolsFeitos() {
		return golsFeitos;
	}

	public void setGolsFeitos(Integer golsFeitos) {
		this.golsFeitos = golsFeitos;
	}

	public Integer getGolsSofridos() {
		return golsSofridos;
	}

	public void setGolsSofridos(Integer golsSofridos) {
		this.golsSofridos = golsSofridos;
	}

	public Integer getSaldoDeGols() {
		return saldoDeGols;
	}

	public void setSaldoDeGols(Integer saldoDeGols) {
		this.saldoDeGols = saldoDeGols;
	}

	public Integer getDerrotas() {
		return derrotas;
	}

	public void setDerrotas(Integer derrotas) {
		this.derrotas = derrotas;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public Integer getVitorias() {
		return vitorias;
	}

	public void setVitorias(Integer vitorias) {
		this.vitorias = vitorias;
	}

	public Integer getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(Integer pontuacao) {
		this.pontuacao = pontuacao;
	}

	public Integer getEmpates() {
		return empates;
	}

	public void setEmpates(Integer empates) {
		this.empates = empates;
	}

}
