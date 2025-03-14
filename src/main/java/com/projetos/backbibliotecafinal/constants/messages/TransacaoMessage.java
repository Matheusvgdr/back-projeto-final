package com.projetos.backbibliotecafinal.constants.messages;

public class TransacaoMessage {
    public static final String SAVE_SUCCESS = "Transação realizada com sucesso!";
    public static final String EMPRESTIMO_LOCALIZADO = "Já existe um empréstimo para essa conta, devolva o livro para realizar um novo empréstimo!";
    public static final String INVALID_DATE = "Não é possível realizar um empréstimo com a data de devolução menor do que hoje!";

    public static final String REGISTER_NOT_FOUND = "Não há transação(ões) registrada(s) para esse usuário!";
    public static final String SEARCH_LIST_SUCCESS = "Lista de transações carregada com sucesso!";

    public static final String DEVOLUCAO_EMPRESTIMO = "Empréstimo devolvido com sucesso!";

    public static final String MULTA_EMPRESTIMO = "Multa de %s aplicada por atraso de %s dias";
}
