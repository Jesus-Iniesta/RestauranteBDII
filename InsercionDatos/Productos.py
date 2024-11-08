import csv
import os

#Separacion para productos
def load_csv_productos(filename):
    filepath = os.path.join("data", filename)  # Especifica la ruta de la carpeta
    with open(filepath, 'r',encoding='utf-8') as file:
        return [line.strip() for line in file]

        
    
def main():
    total_productos = load_csv_productos('productos.csv')
    print(total_productos)
if __name__ == '__main__':
    main()