package crud_colmeia;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

public class LoginController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtSenha;
    @FXML private Label lblMensagem;

    // Credenciais fixas
    private static final String USUARIO_CORRETO = "matheus";
    private static final String SENHA_CORRETA = "2107";

    @FXML
    public void initialize() {
        System.out.println("✅ LoginController inicializado - Modo Tela Cheia");
        
        // Configurar navegação por teclado
        configurarNavegacaoTeclado();
        
        // Focar no campo usuário ao iniciar
        txtUsuario.requestFocus();
        
        // Debug - mostrar tamanho da tela
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        System.out.println("📐 Tamanho da tela: " + screenBounds.getWidth() + "x" + screenBounds.getHeight());
    }

    private void configurarNavegacaoTeclado() {
        // No campo usuário: TAB vai para senha, ENTER tenta fazer login
        txtUsuario.setOnKeyPressed(this::tratarTeclaPressionada);
        
        // No campo senha: TAB volta para usuário, ENTER faz login
        txtSenha.setOnKeyPressed(this::tratarTeclaPressionada);
        
        // Eventos específicos para ENTER em cada campo
        txtUsuario.setOnAction(event -> {
            // ENTER no usuário vai para senha
            txtSenha.requestFocus();
        });
        
        txtSenha.setOnAction(event -> {
            // ENTER na senha faz login
            fazerLogin();
        });
    }

    private void tratarTeclaPressionada(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            // ENTER em qualquer campo tenta fazer login
            fazerLogin();
        } else if (event.getCode() == KeyCode.TAB) {
            // TAB navega entre os campos
            if (event.getSource() == txtUsuario) {
                // Se está no usuário, vai para senha
                txtSenha.requestFocus();
                event.consume(); // Impede o comportamento padrão do TAB
            } else if (event.getSource() == txtSenha) {
                // Se está na senha, volta para usuário
                txtUsuario.requestFocus();
                event.consume(); // Impede o comportamento padrão do TAB
            }
        } else if (event.getCode() == KeyCode.ESCAPE) {
            // ESC sai do sistema
            sair();
        }
    }

    @FXML
    private void fazerLogin() {
        String usuario = txtUsuario.getText();
        String senha = txtSenha.getText();

        // Limpar mensagem anterior
        lblMensagem.setVisible(false);

        // Validar campos vazios
        if (usuario.isEmpty() || senha.isEmpty()) {
            mostrarErro("Preencha todos os campos!");
            if (usuario.isEmpty()) {
                txtUsuario.requestFocus();
            } else {
                txtSenha.requestFocus();
            }
            return;
        }

        // Verificar credenciais
        if (usuario.equals(USUARIO_CORRETO) && senha.equals(SENHA_CORRETA)) {
            System.out.println("✅ Login bem-sucedido! Abrindo sistema principal...");
            abrirSistemaPrincipal();
        } else {
            mostrarErro("Usuário ou senha incorretos!");
            // Limpar senha para nova tentativa
            txtSenha.clear();
            txtSenha.requestFocus();
        }
    }

    private void mostrarErro(String mensagem) {
        lblMensagem.setText(mensagem);
        lblMensagem.setVisible(true);
        
        // Fazer a mensagem piscar levemente para chamar atenção
        lblMensagem.setStyle("-fx-text-fill: #ff6b6b; -fx-font-weight: bold; -fx-font-size: 14px;");
    }

    private void abrirSistemaPrincipal() {
        try {
            // Fechar a tela de login
            Stage stageAtual = (Stage) txtUsuario.getScene().getWindow();
            
            // Carregar a tela principal
            Parent root = FXMLLoader.load(getClass().getResource("/telas/view/MainLayout.fxml"));
            Scene scene = new Scene(root);
            
            // Aplicar o CSS
            scene.getStylesheets().add(getClass().getResource("/globalStyle/style.css").toExternalForm());
            
            Stage novoStage = new Stage();
            novoStage.setTitle("Ads horse - Sistema de Adestramento");
            novoStage.setScene(scene);
            novoStage.setMaximized(true); // Tela cheia
            novoStage.show();
            
            // Fechar a tela de login
            stageAtual.close();
            
            System.out.println("🚀 Sistema principal aberto em tela cheia!");
            
        } catch (Exception e) {
            e.printStackTrace();
            mostrarErro("Erro ao carregar o sistema principal");
            
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setHeaderText("Erro ao abrir sistema");
            alerta.setContentText("Não foi possível carregar o sistema principal: " + e.getMessage());
            alerta.showAndWait();
        }
    }

    @FXML
    private void sair() {
        System.out.println("🚪 Saindo do sistema...");
        
        // Confirmação antes de sair
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmação");
        confirmacao.setHeaderText("Sair do sistema");
        confirmacao.setContentText("Tem certeza que deseja sair?");
        
        // Personalizar botões
        ButtonType simButton = new ButtonType("Sim", ButtonBar.ButtonData.YES);
        ButtonType naoButton = new ButtonType("Não", ButtonBar.ButtonData.NO);
        confirmacao.getButtonTypes().setAll(simButton, naoButton);
        
        confirmacao.showAndWait().ifPresent(resposta -> {
            if (resposta == simButton) {
                Stage stage = (Stage) txtUsuario.getScene().getWindow();
                stage.close();
            }
        });
    }

    // Métodos adicionados para corrigir os erros - eventos do "Esqueci a senha"
    @FXML
    private void onHoverEsqueciSenha() {
        // Método para quando o mouse entrar sobre "Esqueci a senha"
        System.out.println("Mouse sobre 'Esqueci a senha'");
    }

    @FXML
    private void onHoverEsqueciSenhaSair() {
        // Método para quando o mouse sair de "Esqueci a senha"
        System.out.println("Mouse saiu de 'Esqueci a senha'");
    }

    @FXML
    private void onClickEsqueciSenha() {
        // Método para quando clicar em "Esqueci a senha"
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Esqueci a Senha");
        alerta.setHeaderText("Recuperação de Senha");
        alerta.setContentText("Entre em contato com o administrador do sistema.\nUsuário: matheus\nSenha: 2107");
        alerta.showAndWait();
    }

    // Métodos para melhorar a experiência do usuário
    @FXML
    private void onUsuarioClicked() {
        // Quando clicar no campo usuário, selecionar todo o texto
        txtUsuario.selectAll();
    }

    @FXML
    private void onSenhaClicked() {
        // Quando clicar no campo senha, selecionar todo o texto
        txtSenha.selectAll();
    }

    @FXML
    private void onUsuarioEnter() {
        // Quando terminar de digitar no usuário (não confundir com ENTER)
        if (!txtUsuario.getText().isEmpty()) {
            txtSenha.requestFocus();
        }
    }

    // Método para limpar campos
    @FXML
    private void limparCampos() {
        txtUsuario.clear();
        txtSenha.clear();
        lblMensagem.setVisible(false);
        txtUsuario.requestFocus();
    }

    // Método chamado quando a tela é mostrada
    public void onTelaMostrada() {
        System.out.println("🎬 Tela de login mostrada em tela cheia");
        txtUsuario.requestFocus();
    }
}