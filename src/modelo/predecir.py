import sys
import pandas as pd
import joblib
from tensorflow import keras
import numpy as np

def main():
    modelo = keras.models.load_model('ann.h5')
    minmaxs = joblib.load('minmaxs.pkl')

    if len(sys.argv) < 14:  # 31 características + nombre del script
        print("Número incorrecto de argumentos. Se requieren 31 valores de características.")
        return

    # Leer los datos del cmd
    datos_ingresados = [float(num) for num in sys.argv[1:]]

    datos_a_predecir = np.array(datos_ingresados)

    # print(datos_a_predecir)
    nombres_caracteristicas = ['Bodega', 'Codigo', 'Articulo', 'Modelo', 'Marca', 'Clase', 'SubGrupo', 'Cantidad', 'Precio', 'Iva', 'CostoPromedio', 'CostoTotal', 'CANAL']

    datos_df = pd.DataFrame([datos_a_predecir], columns=nombres_caracteristicas)
    datos_normalizados = minmaxs.transform(datos_df)

    datos_escalados = minmaxs.transform(datos_normalizados)

    predicciones = modelo.predict(datos_escalados)
    predicciones_str = np.array2string(predicciones)

    # print(predicciones)
    # print(predicciones.tolist())
    print(predicciones_str)
    sys.stdout.flush()



if __name__ == "__main__":
    main()
