import psycopg2

# Conexión a la base de datos
def connect_to_db():
    return psycopg2.connect(
        host="localhost",
        dbname="prueba_proyecto",
        user="postgres",
        password="1234"
    )