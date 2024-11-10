from faker import Faker
import csv
from datetime import timedelta
import os
from EmpleadoFaker import limpiar_pantalla
from conexion import connect_to_db

fake = Faker()

#Separacion para almacen
def load_csv_almacen(filename):
    filepath = os.path.join("data", filename)  # Especifica la ruta de la carpeta
    categorias = []
    with open(filepath, 'r', encoding='utf-8') as file:
        reader = csv.reader(file, delimiter=',', quotechar="'")  # Usa quotechar para manejar comillas simples
        for row in reader:
            if len(row) == 3:  # Asegura que tenga ambas columnas
                categorias.append([row[0].strip(), row[1].strip(),row[2].strip()])  # Agrega como tupla y elimina espacios en blanco
    return categorias
#Insertar datos en almacen
def insert_data_horario(cursor,horario):
    i = 0
    for dia,inicio,fin in horario:
        query = """
        INSERT INTO restaurante.horario(dia,hora_inicio,hora_fin)
        VALUES(%s,%s,%s)
        """
        cursor.execute(query, (dia,inicio,fin))
        print(f"Inserción: {i}",end='\r')
        i += 1
def main():
    horario = load_csv_almacen('horario.csv')
    limpiar_pantalla()
    conn = connect_to_db()
    cursor = conn.cursor()
    try:
        insert_data_horario(cursor,horario)
        conn.commit()
        print("\nInserción horarios terminada")
    except Exception as e:
        print("Error: ",e)
        conn.rollback()
    finally:
        cursor.close()
        conn.close()
    
if __name__ == '__main__':
    main()