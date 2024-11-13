import psycopg2

# Conexión a la base de datos
def connect_to_db():
    return psycopg2.connect(
        host="100.88.248.149",
        dbname="Restaurante",
        user="postgres",
        password="1234"
    )