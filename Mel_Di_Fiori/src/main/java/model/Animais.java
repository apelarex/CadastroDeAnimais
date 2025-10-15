package model;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "Animais")
public class Animais {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(nullable = false) // campo obrigatório (NOT NULL no banco)
    private String identificacao;

    @Column(nullable = false)
    private String localizacao;

    @Column(nullable = false)
	private String raça;

    @Column(nullable = false)
	private String status;

    @Column(nullable = false)
	private LocalDate datadenascimento;

	private int peso;

	@Column(length = 2000)
    private String observacoes;




    // 🔄 Construtor vazio exigido pelo JPA
	public Animais() {
	}

	 // ✅ Construtor com todos os atributos
	public Animais(String identificacao, String localizacao, String raça, String status, LocalDate datadenascimento,
			int peso, String observacoes) {
		this.identificacao = identificacao;
		this.localizacao = localizacao;
		this.raça = raça;
		this.status = status;
		this.datadenascimento = datadenascimento;
		this.peso = peso;
		this.observacoes = observacoes;
	}

    // ➕ Getters e Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public String getRaça() {
		return raça;
	}

	public void setRaça(String raça) {
		this.raça = raça;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getDataDeNascimento() {
		return datadenascimento;
	}

	public void setDataDeNascimento(LocalDate datadenascimento) {
		this.datadenascimento = datadenascimento;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	@Override
	public String toString() {
		return "Animais[id=" + id + ", identificacao=" + identificacao + ", localizacao=" + localizacao + ", raça="
				+ raça+ ", status=" + status + ", datadenascimento=" + datadenascimento+ ", peso="
				+ peso + ", observacoes=" + observacoes + "]";
	}

}
