# API de Gestão e Aluguel de Veículos

## Descrição do Projeto
API RESTful desenvolvida para o gerenciamento de frotas e automação de sistemas de aluguel de veículos. O sistema permite o controle completo dos registros de veículos e o gerenciamento das locações, garantindo a integridade dos status de disponibilidade e o registro histórico das operações.

## ⚙️ Requisitos Funcionais Implementados
- [x] **Cadastrar veículo**: Registro de novos veículos contendo marca, modelo, ano, placa, valor da diária e status de disponibilidade.
- [x] **Editar veículo**: Atualização de dados cadastrais de um veículo específico.
- [x] **Remover veículo**: Exclusão (física ou lógica) de um veículo do sistema.
- [x] **Listar veículos**: Consulta paginada/completa da frota atualizada.
- [x] **Buscar veículo**: Recuperação de detalhes operacionais de um veículo utilizando o ID ou a Placa.
- [x] **Alugar veículo**: Registro de transação de locação vinculada a um cliente, definindo data de início e término, com alteração automatizada do status do veículo para "alugado".