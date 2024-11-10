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
            if len(row) == 2:  # Asegura que tenga ambas columnas
                categorias.append([row[0].strip(), row[1].strip()])  # Agrega como tupla y elimina espacios en blanco
    return categorias
#Insertar datos en almacen
def insert_data_almacen(cursor,almacen):
    i = 0
    for nombre,stock in almacen:
        query = """
        INSERT INTO restaurante.almacen(nombre,fecha_caducidad,stock)
        VALUES(%s,%s,%s)
        """
        fecha_caducidad = fake.date_between(start_date="+1y",end_date="+5y")
        cursor.execute(query, (nombre,fecha_caducidad,stock))
        print(f"Inserción: {i}",end='\r')
        i += 1
def main():
    almacen = load_csv_almacen('almacen.csv')
    limpiar_pantalla()
    conn = connect_to_db()
    cursor = conn.cursor()
    try:
        insert_data_almacen(cursor,almacen)
        conn.commit()
        print("\nInserción almacen terminada")
    except Exception as e:
        print("Error: ",e)
        conn.rollback()
    finally:
        cursor.close()
        conn.close()
    
if __name__ == '__main__':
    main()