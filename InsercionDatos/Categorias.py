import csv
import os
from conexion import connect_to_db

#Separacion para categorias
def load_csv_categorias(filename):
    filepath = os.path.join("data", filename)  # Especifica la ruta de la carpeta
    categorias = []
    with open(filepath, 'r', encoding='utf-8') as file:
        reader = csv.reader(file, delimiter=',', quotechar="'")  # Usa quotechar para manejar comillas simples
        for row in reader:
            if len(row) == 2:  # Asegura que tenga ambas columnas
                categorias.append([row[0].strip(), row[1].strip()])  # Agrega como tupla y elimina espacios en blanco
    return categorias
#Insertar datos en tabla de categorias
def insert_data_categorias(cursor,categorias):
    i = 0
    for categoria, descripcion in categorias:
        query = """
        INSERT INTO restaurante.categoria (categorias,descripcion)
        VALUES (%s,%s)
        """
        cursor.execute(query, (categoria, descripcion))
        print(f"Inserción CATEGORIAS: {i+1}",end='\r')
        i += 1
def main():
    total_categorias = load_csv_categorias('categoria.csv')
    
    #Conexion
    conn = connect_to_db()
    cursor = conn.cursor()
    try:
        insert_data_categorias(cursor,total_categorias)
        conn.commit()
        print("\nInserción categorias terminada")
    except Exception as e:
        print("Error:", e)
        conn.rollback()
    finally:
        cursor.close()
        conn.close()
if __name__ == '__main__':
    main()