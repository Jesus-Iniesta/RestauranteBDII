#ESTO ES UNA PRUEBA, NO DEBE EJECUTARSE EN LA BASE DE DATOS DEL PROYECTO
import psycopg2

connection = psycopg2.connect(
    host = "localhost",
    user = "postgres",
    password = "1234",
    database = "prueba_proyecto"
)

connection.autocommit = True

def consultarDatos():
    cursor = connection.cursor()
    query = "Select * from prueba_bd.restaurante"
    try: 
        cursor.execute(query)
    except:
        print("Error ")
    cursor.close()

def insertarDatos_restaurante():
    cursor = connection.cursor()
    query =  f"""INSERT INTO restaurante (nombre, telefono, direccion)VALUES ('Nombre del Restaurante',
    '123-456-7890',
    ROW('Calle Ejemplo', 'Colonia Ejemplo', 'Pais Ejemplo', '12345')::direccion)"""
    cursor.execute(query)
    cursor.close()

print("Hola")   

print("Otro hola")