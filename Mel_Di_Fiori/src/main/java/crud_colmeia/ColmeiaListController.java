package crud_colmeia;

import dao.DAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import model.Animais;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ColmeiaListController {

    // =====================================
    // Elementos da tela vinculados pelo FXML
    @FXML private TableView<Animais> tableAnimal;
    @FXML private TableColumn<Animais, Long> colId;
    @FXML private TableColumn<Animais, String> colIdentificacao;
    @FXML private TableColumn<Animais, String> colLocalizacao;
    @FXML private TableColumn<Animais, String> colRaça;
    @FXML private TableColumn<Animais, String> colStatus;
    @FXML private TableColumn<Animais, String> colData;
    @FXML private TableColumn<Animais, Integer> Peso; // Mantido como Peso (igual ao FXML)

    // Lista observável para atualizar a TableView automaticamente
    private final ObservableList<Animais> dados = FXCollections.observableArrayList();

    // =====================================
    // Inicialização da tela
    @FXML
    public void initialize() {
        // Configura colunas da tabela
        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getId()));
        colIdentificacao.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getIdentificacao()));
        colLocalizacao.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getLocalizacao()));
        colRaça.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getRaça()));
        colStatus.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getStatus()));

        // Coluna de Data com tratamento de valor nulo
        colData.setCellValueFactory(c ->
            new javafx.beans.property.SimpleStringProperty(
                c.getValue().getDataDeNascimento() != null
                    ? c.getValue().getDataDeNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    : "Não definida"
            )
        );

        // CORRIGIDO: Agora Peso não será mais null
        Peso.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getPeso()));

        // Carrega os dados do banco
        carregarAnimais();
    }

    // =====================================
    // Carrega os animais do banco usando DAO
    private void carregarAnimais() {
        List<Animais> lista = new DAO<>(Animais.class).obterTodos(100, 0);
        dados.setAll(lista);
        tableAnimal.setItems(dados);
    }

    // =====================================
    // Botão Cadastrar
    @FXML
    private void abrirCadastro() {
        try {
            Node tela = FXMLLoader.load(getClass().getResource("/telas/view/TelaCadastroColmeia.fxml"));
            StackPane painel = (StackPane) tableAnimal.getScene().lookup("#painelConteudo");
            painel.getChildren().setAll(tela);
        } catch (IOException e) {
            e.printStackTrace();
            mostrarErro("Erro ao carregar tela de cadastro: " + e.getMessage());
        }
    }

    // =====================================
    // Botão Editar - AGORA FUNCIONAL
    @FXML
    private void editarAnimal() {
        Animais selecionado = tableAnimal.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selecione um animal para editar.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/telas/view/TelaEditColmeia.fxml"));
            Node tela = loader.load();
            
            // Obtém o controlador e passa o animal selecionado
            ColmeiaEditController controller = loader.getController();
            controller.setAnimal(selecionado);
            
            StackPane painel = (StackPane) tableAnimal.getScene().lookup("#painelConteudo");
            painel.getChildren().setAll(tela);
        } catch (IOException e) {
            e.printStackTrace();
            mostrarErro("Erro ao carregar tela de edição: " + e.getMessage());
        }
    }

    // =====================================
    // Botão Excluir
    @FXML
    private void excluirColmeia() {
        Animais selecionado = tableAnimal.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selecione um animal para excluir.");
            return;
        }

        // Confirmação antes de excluir
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Exclusão");
        confirmacao.setHeaderText("Excluir Animal");
        confirmacao.setContentText("Tem certeza que deseja excluir o animal: " + selecionado.getIdentificacao() + "?");
        
        if (confirmacao.showAndWait().get() == ButtonType.OK) {
            new DAO<>(Animais.class).removerPorIdTransacional(selecionado.getId());
            carregarAnimais(); // Recarrega a tabela
            mostrarAlerta(Alert.AlertType.INFORMATION, "Animal excluído com sucesso!");
        }
    }

    // =====================================
    // Métodos auxiliares para exibir alertas
    private void mostrarAlerta(Alert.AlertType tipo, String mensagem) {
        Alert alerta = new Alert(tipo);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    private void mostrarErro(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Erro");
        alerta.setHeaderText("Ocorreu um erro");
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}