import axios from 'axios';

const API = 'http://localhost:8080/api/beneficios';

export async function listar() {
  const { data } = await axios.get(API);
  return data;
}

export async function transferir(origemId: number, destinoId: number, valor: number) {
  await axios.post(`${API}/transferencia`, { origemId, destinoId, valor });
}
