package cadastrausuariofatec;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicButtonUI;

// Tela de cadastro de usuário
public class TelaCadastro extends JFrame {

    // cores da tela
    private static final Color COR_FUNDO = new Color(0xFFF5F8);
    private static final Color COR_TITULO = new Color(0x8E3B5B);
    private static final Color COR_SUAVE = new Color(0x9A6580);
    private static final Color COR_BORDA = new Color(0xF3C1D3);
    private static final Color COR_DESTAQUE = new Color(0xD4608B);
    private static final Color COR_DESTAQUE_HOVER = new Color(0xC04D79);
    private static final Color COR_SECUNDARIO = new Color(0xFDE4ED);
    private static final Color COR_SECUNDARIO_HOVER = new Color(0xFAD0DF);

    // fontes da tela
    private static final Font FONTE_TITULO = new Font(Font.SANS_SERIF, Font.BOLD, 22);
    private static final Font FONTE_SUBTITULO = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
    private static final Font FONTE_ROTULO = new Font(Font.SANS_SERIF, Font.BOLD, 12);
    private static final Font FONTE_CAMPO = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
    private static final Font FONTE_BOTAO = new Font(Font.SANS_SERIF, Font.BOLD, 13);

    // componentes usados nos eventos
    private final JPanel tela;
    private final JTextField txtNome;
    private final JTextField txtUsuario;
    private final JPasswordField passSenha;
    private final JPasswordField passConfSenha;
    private boolean usuarioValido;
    private boolean cadastroValido;
    // mensagem do pop-up e seu tipo (0 = erro, 1 = informação)
    private String mensagemJOption;
    private int mensagemTipo = 0;

    public TelaCadastro() {
        // configurações da janela
        setTitle("Cadastro - Desenvolvimento para Servidores");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // painel principal sem layout (posições manuais)
        tela = new JPanel();
        tela.setBackground(COR_FUNDO);
        tela.setPreferredSize(new Dimension(400, 420));
        tela.setLayout(null);
        setContentPane(tela);

        // título
        JLabel lblIdentificacao = new JLabel("Criar conta", SwingConstants.CENTER);
        lblIdentificacao.setBounds(0, 24, 400, 34);
        lblIdentificacao.setFont(FONTE_TITULO);
        lblIdentificacao.setForeground(COR_TITULO);
        tela.add(lblIdentificacao);

        // subtítulo
        JLabel lblSubtitulo = new JLabel("Preencha os campos abaixo para se cadastrar", SwingConstants.CENTER);
        lblSubtitulo.setBounds(0, 60, 400, 18);
        lblSubtitulo.setFont(FONTE_SUBTITULO);
        lblSubtitulo.setForeground(COR_SUAVE);
        tela.add(lblSubtitulo);

        // campo nome
        tela.add(criaRotuloCampo("Nome", 96));

        txtNome = new JTextField();
        txtNome.setBounds(40, 116, 320, 32);
        txtNome.setColumns(10);
        estilizaCampo(txtNome);
        tela.add(txtNome);

        // campo usuário
        tela.add(criaRotuloCampo("Usuário", 156));

        txtUsuario = new JTextField();
        txtUsuario.setBounds(40, 176, 320, 32);
        txtUsuario.setColumns(10);
        estilizaCampo(txtUsuario);
        tela.add(txtUsuario);

        // campo senha
        tela.add(criaRotuloCampo("Senha", 216));

        passSenha = new JPasswordField();
        passSenha.setBounds(40, 236, 320, 32);
        estilizaCampo(passSenha);
        tela.add(passSenha);

        // campo confirmar senha
        tela.add(criaRotuloCampo("Confirmar senha", 276));

        passConfSenha = new JPasswordField();
        passConfSenha.setBounds(40, 296, 320, 32);
        estilizaCampo(passConfSenha);
        tela.add(passConfSenha);

        // botões
        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBounds(210, 354, 150, 40);
        estilizaBotao(btnCadastrar, COR_DESTAQUE, COR_DESTAQUE_HOVER, Color.WHITE, COR_DESTAQUE);
        tela.add(btnCadastrar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(40, 354, 150, 40);
        estilizaBotao(btnCancelar, COR_SECUNDARIO, COR_SECUNDARIO_HOVER, COR_TITULO, COR_BORDA);
        tela.add(btnCancelar);

        // botão Cancelar: volta para o login
        btnCancelar.addActionListener((ActionEvent e) -> {
            TelaLogin tLogin = new TelaLogin();
            tLogin.abreTela();
            dispose();
        });

        // botão Cadastrar: valida os campos e grava no banco
        btnCadastrar.addActionListener((ActionEvent e) -> {
            // guarda o que foi digitado
            Usuario usu = new Usuario();
            usu.setNome(txtNome.getText());
            usu.setUsuario(txtUsuario.getText());
            usu.setSenha(new String(passSenha.getPassword()));

            // validações dos campos
            if ("".equals(usu.getNome())) {
                mensagemJOption = "Campo nome do usuário precisa ser informado";
                mensagemTipo = 0;
            } else if ("".equals(usu.getUsuario())) {
                mensagemJOption = "Campo usuário precisa ser informado";
                mensagemTipo = 0;
            } else if (usu.getNome().length() > 30) {
                mensagemJOption = "O nome deve ter no máximo 30 caracteres";
                mensagemTipo = 0;
            } else if (usu.getUsuario().length() > 10) {
                mensagemJOption = "O usuário deve ter no máximo 10 caracteres";
                mensagemTipo = 0;
            } else if ("".equals(usu.getSenha())) {
                mensagemJOption = "Campo senha precisa ser informado";
                mensagemTipo = 0;
            } else if (!usu.getSenha().equals(new String(passConfSenha.getPassword()))) {
                mensagemJOption = "Campos senha e confirmação de senha não coincidem!";
                mensagemTipo = 0;
            } else {
                // verifica se o usuário já existe
                usuarioValido = usu.verificaUsuario(usu.getUsuario());

                if (usuarioValido) {
                    mensagemJOption = "Usuário já existe na base de dados.";
                    mensagemTipo = 0;
                } else {
                    // grava o novo usuário
                    cadastroValido = usu.cadastraUsuario(usu.getNome(), usu.getUsuario(), usu.getSenha());
                    if (cadastroValido) {
                        mensagemJOption = "Usuário cadastrado corretamente!";
                        mensagemTipo = 1;
                    } else {
                        mensagemJOption = "Erro ao tentar se conectar com o Banco de Dados.";
                        mensagemTipo = 0;
                    }
                }
            }

            // mostra a mensagem
            JOptionPane.showMessageDialog(null, mensagemJOption, "Atenção", mensagemTipo);

            // se cadastrou, volta para o login
            if (mensagemTipo == 1) {
                TelaLogin tLogin = new TelaLogin();
                tLogin.abreTela();
                dispose();
            }
        });

        // ajusta o tamanho e centraliza a janela
        pack();
        setLocationRelativeTo(null);
    }

    // cria o rótulo de um campo
    private static JLabel criaRotuloCampo(String texto, int y) {
        JLabel rotulo = new JLabel(texto);
        rotulo.setBounds(40, y, 320, 18);
        rotulo.setFont(FONTE_ROTULO);
        rotulo.setForeground(COR_TITULO);
        return rotulo;
    }

    // aplica o estilo nos campos de texto
    private static void estilizaCampo(JTextField campo) {
        campo.setFont(FONTE_CAMPO);
        campo.setForeground(COR_TITULO);
        campo.setBackground(Color.WHITE);
        campo.setCaretColor(COR_DESTAQUE);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_BORDA, 1),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)));
    }

    // aplica o estilo nos botões e troca a cor ao passar o mouse
    private static void estilizaBotao(JButton botao, Color fundo, Color fundoHover,
            Color texto, Color borda) {
        botao.setUI(new BasicButtonUI());
        botao.setFont(FONTE_BOTAO);
        botao.setForeground(texto);
        botao.setBackground(fundo);
        botao.setOpaque(true);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createLineBorder(borda, 1));
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                botao.setBackground(fundoHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botao.setBackground(fundo);
            }
        });
    }

    // mostra a tela de cadastro
    public void abreTela() {
        TelaCadastro telaCadastro = new TelaCadastro();
        telaCadastro.setVisible(true);
    }
}
