import csv
import os
import random
from conexion import connect_to_db

#Separacion para productos
def load_csv_productos(filename):
    filepath = os.path.join("data", filename)  # Especifica la ruta de la carpeta
    categorias = []
    with open(filepath, 'r', encoding='utf-8') as file:
        reader = csv.reader(file, delimiter=',', quotechar="'")  # Usa quotechar para manejar comillas simples
        for row in reader:
            if len(row) == 5:  # Asegura que tenga ambas columnas
                categorias.append([row[0].strip(), row[1].strip(),row[2].strip(),row[3].strip(),row[4].strip()])  # Agrega como tupla y elimina espacios en blanco
    return categorias
#Método para insertar datos de productos
def insert_data_products(cursor,productos):
    i = 0
    for nombre, precio, stock, description, id_cat in productos:
        query = """
        INSERT INTO restaurante.productos(nombre, precio, stock, descripcion, id_cat, id_proveedor)
        VALUES(%s, %s, %s, %s, %s, %s)
        """
        try:
            # Conversión de tipos
            precio_doble = float(precio)
            stock_int = int(stock)
            id_cat_int = int(id_cat)  # Conversión de id_cat a entero
            id_proveedor = random.randint(1, 10_000)

            cursor.execute(query, (nombre, precio_doble, stock_int, description, id_cat_int, id_proveedor))
            print(f"Inserción PRODUCTO: {i+1}", end='\r')
            i += 1
        except ValueError as e:
            print(f"Error de conversión en el producto '{nombre}': {e}")
        
    
def main():
    total_productos = load_csv_productos('productos.csv')
    conn = connect_to_db()
    cursor = conn.cursor()
    try:
        insert_data_products(cursor,total_productos)
        conn.commit()
        print("\nInserción productos terminada")
    except Exception as e:
        print("Error: ",e)
        conn.rollback()
    finally:
        cursor.close()
        conn.close()
    
if __name__ == '__main__':
    main()