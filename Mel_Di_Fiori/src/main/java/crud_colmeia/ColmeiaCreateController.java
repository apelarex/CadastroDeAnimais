package crud_colmeia;

// Importa o DAO genérico para salvar no banco
import dao.DAO;

// Importações do JavaFX (componentes gráficos)
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Animais;


import java.time.LocalDate;

/**
 * 🚀 ColmeiaCreateController ----------------------------------------- Este
 * controller gerencia a tela de cadastro de colmeias. Ele coleta os dados
 * preenchidos pelo usuário e os salva no banco, além de configurar os campos
 * como ComboBox e Spinner na inicialização.
 */
public class ColmeiaCreateController {

	// Campo de texto para o número da colmeia
	@FXML
	private TextField txtNumero;

	// Campo para selecionar a data de instalação
	@FXML
	private DatePicker DataDeNascimento;

	// Campo de texto para localização da colmeia
	@FXML
	private TextField txtLocalizacao;

	// ComboBox para selecionar a situação da colmeia
	@FXML
	private ComboBox<String> comboSituacao;

	// ComboBox para selecionar o tipo da colmeia
	@FXML
	private ComboBox<String> comboRaça;

	// Spinner para escolher o número de quadros
	@FXML
	private Spinner<Integer> spinnerPeso;

	// Área de texto para observações adicionais
	@FXML
	private TextArea txtObservacoes;

	/**
	 * Este método é automaticamente chamado ao carregar o FXML. Ele configura os
	 * ComboBox e o Spinner com valores padrão.
	 */
	@FXML
	public void initialize() {
		// Adiciona opções ao ComboBox de situação
		comboSituacao.getItems().addAll("Saudavel", "doente", "adestrado", "Em adestramento");

		// Adiciona opções ao ComboBox de tipo
		comboRaça.getItems().addAll("Árabe", "Mangalarga Marchador", "Puro Sangue", "Percheron", "Paint Horse", "Outros");

		// Configura o spinner: mínimo 1, máximo 20, valor inicial 10
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 10);
		spinnerPeso.setValueFactory(valueFactory);
	}

	/**
	 * Método para limpar o estilo visual de erro nos campos (remover borda
	 * vermelha).
	 */
	private void limparEstiloErro() {
		limparBordaVermelha(txtNumero);
		limparBordaVermelha(txtLocalizacao);
		limparBordaVermelha(comboRaça);
		limparBordaVermelha(comboSituacao);
		limparBordaVermelha(DataDeNascimento);
	}

	private void colocarBordaVermelha(Control campo) {
		campo.setStyle("-fx-border-color: red; -fx-border-width: 2;");
	}

	private void limparBordaVermelha(Control campo) {
		campo.setStyle(""); // Remove estilo inline para voltar ao normal
	}

	/**
	 * Validação com realce visual dos campos obrigatórios. Retorna true se todos
	 * ok, ou false se há erros.
	 */
	private boolean validarCamposComVisual() {
		limparEstiloErro();

		boolean valido = true;

		if (txtNumero.getText() == null || txtNumero.getText().trim().isEmpty()) {
			colocarBordaVermelha(txtNumero);
			valido = false;
		}

		if (txtLocalizacao.getText() == null || txtLocalizacao.getText().trim().isEmpty()) {
			colocarBordaVermelha(txtLocalizacao);
			valido = false;
		}

		if (comboRaça.getValue() == null || comboRaça.getValue().trim().isEmpty()) {
			colocarBordaVermelha(comboRaça);
			valido = false;
		}

		if (comboSituacao.getValue() == null || comboSituacao.getValue().trim().isEmpty()) {
			colocarBordaVermelha(comboSituacao);
			valido = false;
		}

		if (DataDeNascimento.getValue() == null) {
			colocarBordaVermelha(DataDeNascimento);
			valido = false;
		}

		return valido;
	}

	/**
	 * Método executado ao clicar no botão "Salvar". Ele coleta os dados da tela,
	 * cria uma nova colmeia e salva no banco usando o DAO.
	 */
	@FXML
	private void salvarAnimal() {
		try {
			if (!validarCamposComVisual()) {
				Alert alerta = new Alert(Alert.AlertType.WARNING);
				alerta.setTitle("Campos Obrigatórios");
				alerta.setHeaderText("Preencha os campos obrigatórios destacados em vermelho.");
				alerta.setContentText("Os campos com borda vermelha são obrigatórios e não podem ficar vazios.");
				alerta.showAndWait();
				return;
			}

			// continua o salvamento normalmente
			String numero = txtNumero.getText();
			LocalDate data = DataDeNascimento.getValue();
			String local = txtLocalizacao.getText();
			String situacao = comboSituacao.getValue();
			String raça = comboRaça.getValue();
			int peso = spinnerPeso.getValue();
			String obs = txtObservacoes.getText();

			Animais novo = new Animais(numero, local, raça, situacao, data, peso, obs);

			new DAO<>(Animais.class).incluirTransacional(novo);

			Alert alerta = new Alert(Alert.AlertType.INFORMATION);
			alerta.setTitle("Animal");
			alerta.setHeaderText("Sucesso");
			alerta.setContentText("Animal salvo com sucesso!");
			alerta.showAndWait();

			limparCampos();

		} catch (Exception e) {
			e.printStackTrace();

			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setTitle("Erro");
			alerta.setHeaderText("Falha ao salvar animal");
			alerta.setContentText("Erro: " + e.getMessage());
			alerta.showAndWait();
		}
	}

	/**
	 * Limpa todos os campos da tela, deixando prontos para um novo cadastro.
	 */
	@FXML
	private void limparCampos() {
		txtNumero.clear();
		DataDeNascimento.setValue(null);
		txtLocalizacao.clear();
		comboSituacao.setValue(null);
		comboRaça.setValue(null);
		spinnerPeso.getValueFactory().setValue(10);
		txtObservacoes.clear();
	}
}