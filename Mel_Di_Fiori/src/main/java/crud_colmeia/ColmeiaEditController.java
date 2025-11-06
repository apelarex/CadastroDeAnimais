package crud_colmeia;

import dao.DAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import model.Animais;

import java.io.IOException;

public class ColmeiaEditController {

    @FXML private TextField txtNumero;
    @FXML private DatePicker DataDeNascimento;
    @FXML private TextField txtLocalizacao;
    @FXML private ComboBox<String> comboSituacao;
    @FXML private ComboBox<String> comboRaça;
    @FXML private Spinner<Integer> spinnerPeso;
    @FXML private TextArea txtObservacoes;

    private Animais animalEditado;
    private boolean inicializado = false;

    @FXML
    public void initialize() {
        System.out.println("✅ ColmeiaEditController inicializado");
        
        // Configurar comboboxes e spinner
        if (comboSituacao != null) {
            comboSituacao.getItems().addAll("Saudavel", "doente", "adestrado", "Em adestramento");
        }
        if (comboRaça != null) {
            comboRaça.getItems().addAll("Árabe", "Mangalarga Marchador", "Puro Sangue", "Percheron", "Paint Horse", "Outros");
        }
        if (spinnerPeso != null) {
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20, 10);
            spinnerPeso.setValueFactory(valueFactory);
        }
        
        inicializado = true;
        
        // Se já tiver um animal para editar, popula os campos
        if (animalEditado != null) {
            popularCampos();
        }
    }

    public void setAnimal(Animais animal) {
        this.animalEditado = animal;
        System.out.println("📝 Animal recebido para edição: " + animal);
        
        // Se já estiver inicializado, popula os campos imediatamente
        if (inicializado) {
            popularCampos();
        }
    }

    private void popularCampos() {
        if (animalEditado != null) {
            System.out.println("🔄 Populando campos com dados do animal...");
            
            safeSetText(txtNumero, animalEditado.getIdentificacao());
            safeSetDate(DataDeNascimento, animalEditado.getDataDeNascimento());
            safeSetText(txtLocalizacao, animalEditado.getLocalizacao());
            safeSetCombo(comboSituacao, animalEditado.getStatus());
            safeSetCombo(comboRaça, animalEditado.getRaça());
            safeSetSpinner(spinnerPeso, animalEditado.getPeso());
            safeSetText(txtObservacoes, animalEditado.getObservacoes());
        }
    }

    // Métodos auxiliares para evitar NullPointerException
    private void safeSetText(TextField field, String value) {
        if (field != null && value != null) {
            field.setText(value);
        }
    }

    private void safeSetText(TextArea area, String value) {
        if (area != null && value != null) {
            area.setText(value);
        }
    }

    private void safeSetDate(DatePicker picker, java.time.LocalDate value) {
        if (picker != null && value != null) {
            picker.setValue(value);
        }
    }

    private void safeSetCombo(ComboBox<String> combo, String value) {
        if (combo != null && value != null) {
            combo.setValue(value);
        }
    }

    private void safeSetSpinner(Spinner<Integer> spinner, Integer value) {
        if (spinner != null && value != null && spinner.getValueFactory() != null) {
            spinner.getValueFactory().setValue(value);
        }
    }

    // Validação visual dos campos obrigatórios
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

    private void colocarBordaVermelha(Control campo) {
        if (campo != null) {
            campo.setStyle("-fx-border-color: red; -fx-border-width: 2;");
        }
    }

    private void limparEstiloErro() {
        limparBordaVermelha(txtNumero);
        limparBordaVermelha(txtLocalizacao);
        limparBordaVermelha(comboRaça);
        limparBordaVermelha(comboSituacao);
        limparBordaVermelha(DataDeNascimento);
    }

    private void limparBordaVermelha(Control campo) {
        if (campo != null) {
            campo.setStyle("");
        }
    }

    // Botão salvar alterações
    @FXML
    private void salvarAlteracoes() {
        try {
            if (!validarCamposComVisual()) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Campos Obrigatórios");
                alerta.setHeaderText("Preencha os campos obrigatórios destacados em vermelho.");
                alerta.setContentText("Os campos com borda vermelha são obrigatórios e não podem ficar vazios.");
                alerta.showAndWait();
                return;
            }

            // Atualizar os dados do animal
            animalEditado.setIdentificacao(txtNumero.getText());
            animalEditado.setDataDeNascimento(DataDeNascimento.getValue());
            animalEditado.setLocalizacao(txtLocalizacao.getText());
            animalEditado.setStatus(comboSituacao.getValue());
            animalEditado.setRaça(comboRaça.getValue());
            animalEditado.setPeso(spinnerPeso.getValue());
            animalEditado.setObservacoes(txtObservacoes.getText());

            // Atualizar no banco
            new DAO<>(Animais.class).atualizarTransacional(animalEditado);

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Animal");
            alerta.setHeaderText("Sucesso");
            alerta.setContentText("Animal atualizado com sucesso!");
            alerta.showAndWait();

            // Voltar para a lista após salvar
            voltarParaLista();

        } catch (Exception e) {
            e.printStackTrace();
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setHeaderText("Falha ao atualizar animal");
            alerta.setContentText("Erro: " + e.getMessage());
            alerta.showAndWait();
        }
    }

    // Botão cancelar edição
    @FXML
    private void cancelar() {
        voltarParaLista();
    }

    // Método para voltar para a tela de lista
    private void voltarParaLista() {
        try {
            Node tela = FXMLLoader.load(getClass().getResource("/telas/view/TelaListaColmeia.fxml"));
            StackPane painel = (StackPane) txtNumero.getScene().lookup("#painelConteudo");
            painel.getChildren().setAll(tela);
        } catch (IOException e) {
            e.printStackTrace();
            mostrarErro("Erro ao voltar para a lista: " + e.getMessage());
        }
    }

    private void mostrarErro(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Erro");
        alerta.setHeaderText("Ocorreu um erro");
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}