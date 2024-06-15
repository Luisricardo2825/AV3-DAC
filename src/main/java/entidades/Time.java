package entidades;

import java.util.List;

public enum Time {
	A,
	B,
	C;

	public boolean equals(Time time) {
		return this == time;
	}

	public List<String> getTimes() {
		return List.of(A.toString(), B.toString(), C.toString());
	}

	public List<Jogo> getVitorias(List<Jogo> jogos) {
		return jogos.stream().filter(j -> j.getVencedor() == this).toList();
	}

	public List<Jogo> getDerrotas(List<Jogo> jogos) {
		return jogos.stream().filter(j -> j.getVencedor() != this && j.getVencedor() != null).toList();
	}

	public List<Jogo> getEmpates(List<Jogo> jogos) {
		return jogos.stream().filter(j -> j.isEmpate() && (j.time1 == this || j.time2 == this)).toList();
	}
}