import csv
import os
from conexion import connect_to_db


#Separacion para almacen
def load_csv_puestos(filename):
    filepath = os.path.join("data", filename)  # Especifica la ruta de la carpeta
    categorias = []
    with open(filepath, 'r', encoding='utf-8') as file:
        reader = csv.reader(file, delimiter=',', quotechar="'")  # Usa quotechar para manejar comillas simples
        for row in reader:
            if len(row) == 2:  # Asegura que tenga ambas columnas
                categorias.append([row[0].strip(), row[1].strip()])  # Agrega como tupla y elimina espacios en blanco
    return categorias
#Insertar datos en almacen
def insert_data_almacen(cursor,puestos):
    i = 0
    for titulo,descripcion in puestos:
        query = """
        INSERT INTO restaurante.puesto(titulo_puesto,descripcion)
        VALUES(%s,%s)
        """
        cursor.execute(query, (titulo,descripcion))
        print(f"Inserción: {i+1}",end='\r')
        i += 1
def main():
    puestos = load_csv_puestos('puestos.csv')
    conn = connect_to_db()
    cursor = conn.cursor()
    try:
        insert_data_almacen(cursor,puestos)
        conn.commit()
        print("\nInserción puesto terminada")
    except Exception as e:
        print("Error: ",e)
        conn.rollback()
    finally:
        cursor.close()
        conn.close()
    
if __name__ == '__main__':
    main()