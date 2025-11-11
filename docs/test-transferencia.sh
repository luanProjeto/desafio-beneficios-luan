#!/bin/bash
set -e
API="http://localhost:8080/api/beneficios"

echo "📋 Benefícios iniciais:"
curl -s $API | jq . || curl -s $API

echo "💸 Transferindo 150 de 1 → 2 ..."
curl -s -X POST "$API/transferencia"   -H "Content-Type: application/json"   -d '{"origemId":1,"destinoId":2,"valor":150.00}'

echo
echo "📋 Benefícios após transferência:"
curl -s $API | jq . || curl -s $API
