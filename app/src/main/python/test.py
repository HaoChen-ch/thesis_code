# def add(a, b):
#     return a + b


from tsfresh.feature_extraction.feature_calculators import cid_ce
import pandas as pd

def add(a, b):
    data = pd.Series([1, 2, 3, 4, 5, 6, 7, 8])
    data = cid_ce(data, True)
    return data
