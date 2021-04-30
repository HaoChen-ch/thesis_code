from scipy.stats import entropy, skew, kurtosis, iqr
from tsfresh.feature_extraction.feature_calculators import *
import pandas as pd
import numpy as np

def fftfun(data):
    features = np.zeros(21)

    f, Pxx_den = welch(data, 50)

    indices_of_max = Pxx_den.argsort()[-3:][::-1]
    features[0:3] = (f[indices_of_max])
    features[3:6] = (Pxx_den[indices_of_max])
    Y = np.fft.rfft(data)
    energy_feat = np.sum(np.square(np.abs(Y))) / len(data)
    entropy_feat = entropy(np.abs(Y))

    features[6] = energy_feat
    features[7] = entropy_feat
    total_fft_sum = np.sum(np.square(Pxx_den))
    bin1 = np.sum(np.square(Pxx_den[:5])) / total_fft_sum
    bin2 = np.sum(np.square(Pxx_den[5:10])) / total_fft_sum
    bin3 = np.sum(np.square(Pxx_den[10:15])) / total_fft_sum
    bin4 = np.sum(np.square(Pxx_den[15:20])) / total_fft_sum
    bin5 = np.sum(np.square(Pxx_den[20:25])) / total_fft_sum
    bin6 = np.sum(np.square(Pxx_den[25:30])) / total_fft_sum
    bin7 = np.sum(np.square(Pxx_den[30:35])) / total_fft_sum
    bin8 = np.sum(np.square(Pxx_den[35:40])) / total_fft_sum
    bin9 = np.sum(np.square(Pxx_den[40:45])) / total_fft_sum
    bin10 = np.sum(np.square(Pxx_den[45:])) / total_fft_sum
    features[8:18] = [bin1, bin2, bin3, bin4, bin5, bin6, bin7, bin8, bin9, bin10]
    skewness = skew(Pxx_den)
    kurtos = kurtosis(Pxx_den)
    interquart = iqr(data)
    features[18:21] = [skewness, kurtos, interquart]
    return pd.Series(features)


def fun_fft_coefficient(data, param):
    re = data.apply(lambda x: fft_coefficient(x, [{'coeff': 2, 'attr': param}]))
    re = re.apply(lambda x: list(*zip(x))[0][1])
    return re


def fun_time_reversal_asymmetry_statistic(data):
    re = data.apply(lambda x: time_reversal_asymmetry_statistic(x, 200))
    return re


def fun_cid_ce(data):
    re = data.apply(lambda x: cid_ce(x, True))
    return re


def fun_autocorrelation(data):
    re = data.apply(lambda x: autocorrelation(x, 200))

    return re


def fun_ratio_beyond_r_sigma(data):
    re = data.apply(lambda x: ratio_beyond_r_sigma(x, 1))
    return re


def fun_spkt_welch_density(data):
    param = [{"coeff": 1}]
    re = data.apply(lambda x: spkt_welch_density(x=x, param=param))
    re = re.apply(lambda x: list(*zip(x))[0][1])
    return re


def fun_index_mass_quantile(x):
    re = x.apply(lambda x: index_mass_quantile(x, [{"q": 0.65}]))
    re = re.apply(lambda x: list(*zip(x))[0][1])
    return re


def fun(x, f):
    re = x.apply(lambda x: fft_aggregated(x, [{"aggtype": f}]))
    re = re.apply(lambda x: list(*zip(x))[0][1])
    return re


def fun_variance(x):
    re = x.apply(lambda x: fft_aggregated(x, [{"aggtype": "variance"}]))
    re = re.apply(lambda x: list(*zip(x))[0][1])
    return re


def feature_calculator(data):
    result = []

    res = data.apply(fftfun)


    result.extend(res.values.flatten().tolist())

    fft_coefficient_real = fun_fft_coefficient(data, "real")

    result.extend(fft_coefficient_real.values.tolist())

    fft_coefficient_imag = fun_fft_coefficient(data, "imag")

    result.extend(fft_coefficient_imag.tolist())

    fft_coefficient_abs = fun_fft_coefficient(data, "abs")
    result.extend(fft_coefficient_abs.tolist())

    fft_coefficient_angle = fun_fft_coefficient(data, "angle")

    result.extend(fft_coefficient_angle.tolist())

    time_reversal_asymmetry_statistic = fun_time_reversal_asymmetry_statistic(data)

    result.extend(time_reversal_asymmetry_statistic.tolist())

    cid_ce = fun_cid_ce(data)

    result.extend(cid_ce.tolist())

    autocorrelation = fun_autocorrelation(data)

    result.extend(autocorrelation.tolist())

    ratio_beyond_r_sigma = fun_ratio_beyond_r_sigma(data)

    result.extend(ratio_beyond_r_sigma.tolist())

    spkt_welch_density = fun_spkt_welch_density(data)

    result.extend(spkt_welch_density.tolist())

    mean_second_derivative_central1 = data.apply(mean_second_derivative_central)

    result.extend(mean_second_derivative_central1.tolist())

    index_mass_quantile = fun_index_mass_quantile(data)



    result.extend(index_mass_quantile.tolist())

    fft_aggregated_centroid = fun(data, "centroid")

    result.extend(fft_aggregated_centroid.tolist())

    #  ------------------------------------------------------------
    fft_aggregated_skew = fun(data, "skew")

    result.extend(fft_aggregated_skew.tolist())

    #  -----------------------------------------------------------------------
    fft_aggregated_kurtosis = fun(data, "kurtosis")

    result.extend(fft_aggregated_kurtosis.tolist())

    fft_aggregated_variance = fun_variance(data)


    result.extend(fft_aggregated_variance.tolist())

    # # ---------------------------------abs_energy------------------------------------------
    abs_energy1 = data.apply(abs_energy)

    result.extend(abs_energy1.tolist())
    #
    # # ----------------------------------------numpy的统计函数-------------------------------------------------
    #
    median = data.median()
    mean = data.mean()
    max = data.max()
    min = data.min()
    var = data.var()
    std = data.std()
    result.extend(median.tolist())
    result.extend(mean.tolist())
    result.extend(max.tolist())
    result.extend(min.tolist())
    result.extend(var.tolist())
    result.extend(std.tolist())

    # # # ---------------------------------------------------------------------------------------------------
    count_below_mean1 = data.apply(count_below_mean)


    result.extend(count_below_mean1.tolist())

    # #
    # # # --------------------------------------------------------------------------------------------------------------
    # # #
    absolute_sum_of_changes1 = data.apply(absolute_sum_of_changes)

    result.extend(absolute_sum_of_changes1.tolist())

    # --------------------------------------------------------------------------------------------------------------

    kurtosis1 = data.apply(kurtosis)

    result.extend(kurtosis1.tolist())

    # # #
    # # # # --------------------------------------------------------------------------------------------------------------
    # # #
    mean_abs_change1 = data.apply(mean_abs_change)
    result.extend(mean_abs_change1.tolist())

    skewness1 = data.apply(skewness)

    result.extend(skewness1.tolist())
    # # #
    # # # # -------------------------------------------------------------------------------
    variance_larger_than_standard_deviation1 = data.apply(variance_larger_than_standard_deviation)

    result.extend(variance_larger_than_standard_deviation1.tolist())
    # # # #
    # # # # -------------------------------------------------------------------------------
    percentage_of_reoccurring_values_to_all_values1 = data.apply(percentage_of_reoccurring_values_to_all_values)

    result.extend(percentage_of_reoccurring_values_to_all_values1.tolist())

    # #
    # # #-------------------------------------------------------------------------------
    number_peaks1 = number_peaks(data, 3)
    result.extend(number_peaks1.tolist())
    # result = np.asarray(result).reshape(1,-1)
    # print(result.shape)
    return result


def rotate(data):
    orientation = np.asarray([data['o_w'], data['o_x'], data['o_y'], data['o_z']])
    orien = orientation.T
    rn0 = 1 - 2 * (np.square(orien[:, 2]) + np.square(orien[:, 3]))
    rn1 = 2 * (orien[:, 1] * orien[:, 2] - orien[:, 0] * orien[:, 3])
    rn2 = 2 * (orien[:, 1] * orien[:, 3] + orien[:, 0] * orien[:, 2])

    rn3 = 2 * (orien[:, 1] * orien[:, 2] + orien[:, 0] * orien[:, 3])
    rn4 = 1 - 2 * (np.square(orien[:, 1]) + np.square(orien[:, 3]))
    rn5 = 2 * (orien[:, 2] * orien[:, 3] - orien[:, 0] * orien[:, 1])

    rn6 = 2 * (orien[:, 1] * orien[:, 3] - orien[:, 0] * orien[:, 2])
    rn7 = 2 * (orien[:, 2] * orien[:, 3] + orien[:, 0] * orien[:, 1])
    rn8 = 1 - 2 * (np.square(orien[:, 1]) + np.square(orien[:, 2]))

    acc = np.asarray([data['acc_x'], data['acc_y'], data['acc_z']])
    acc_x = pd.DataFrame(rn0 * acc[0] + rn1 * acc[1] + rn2 * acc[2])
    acc_y = pd.DataFrame(rn3 * acc[0] + rn4 * acc[1] + rn5 * acc[2])
    acc_z = pd.DataFrame(rn6 * acc[0] + rn7 * acc[1] + rn8 * acc[2])
    acc_xyz = pd.DataFrame(np.sqrt(np.square(data['acc_x'] + np.square(data['acc_y']) + np.square(data['acc_z']))))
    acc = pd.DataFrame(np.hstack((acc_xyz, acc_x, acc_y, acc_z)))

    pitch = pd.DataFrame(np.arctan(rn7 / rn8))
    roll = pd.DataFrame(np.arcsin(-rn6))
    yaw = pd.DataFrame(np.arctan(rn3 / rn0))
    orien = pd.DataFrame(orien)
    ori = pd.concat((orien, pitch, roll, yaw), axis=1)
    # 输出格式为['o_w','o_x', 'o_y', 'o_z', 'pitch', 'roll', 'yaw']
    # -----------------------------------------------------------------------------------------------
    # 对m_x,m_y,m_z取平方和之后开根号，作为新的列值，并且对magnetic做坐标转化

    mag = np.asarray([data['mag_x'], data['mag_y'], data['mag_z']])
    mag_x = pd.DataFrame(rn0 * mag[0] + rn1 * mag[1] + rn2 * mag[2])
    mag_y = pd.DataFrame(rn3 * mag[0] + rn4 * mag[1] + rn5 * mag[2])
    mag_z = pd.DataFrame(rn6 * mag[0] + rn7 * mag[1] + rn8 * mag[2])
    mag = mag.T
    ma = np.sqrt(np.square(mag[:, 0]) + np.square(mag[:, 1]) + np.square(mag[:, 2])).reshape(-1, 1)
    magnetic = pd.DataFrame(np.hstack((ma, mag_x, mag_y, mag_z)))
    # 输出格式为['ma','m_x', 'm_y', 'm_z']
    # -----------------------------------------------------------------------------------------------
    gyr_xyz = np.sqrt(np.square(data['gyr_x'] + np.square(data['gyr_y']) + np.square(data['gyr_z'])))
    gra_xyz = np.sqrt(np.square(data['gra_x'] + np.square(data['gra_y']) + np.square(data['gra_z'])))
    lacc_xyz = np.sqrt(np.square(data['lacc_x'] + np.square(data['lacc_y']) + np.square(data['lacc_z'])))

    remain = pd.DataFrame(np.asarray([gyr_xyz, data['gyr_x'], data['gyr_y'], data['gyr_z'],
                                      gra_xyz, data['gra_x'], data['gra_y'], data['gra_z'],
                                      lacc_xyz, data['lacc_x'], data['lacc_y'], data['lacc_z']
                                      ]).T)

    fin = pd.concat((acc, ori, magnetic, remain), axis=1)
    fin.columns = ['acc', 'acc_x', 'acc_y', 'acc_z',
                   'o_w', 'o_x', 'o_y', 'o_z', 'pitch', 'roll', 'yaw',
                   'magnetic', 'm_x', 'm_y', 'm_z',
                   'gyr', 'gy_x', 'gy_y', 'gy_z',
                   'gra', 'g_x', 'g_y', 'g_z',
                   'l', 'l_x', 'l_y', 'l_z']
    return fin


def change(acc_x, acc_y, acc_z, o_w, o_x, o_y, o_z, m_x, m_y, m_z, gy_x, gy_y, gy_z, g_x, g_y, g_z, l_x, l_y, l_z):
    matrix = np.asarray([acc_x, acc_y, acc_z, o_w, o_x, o_y, o_z, m_x, m_y, m_z, gy_x, gy_y, gy_z, g_x, g_y, g_z, l_x,
                         l_y, l_z], dtype='float').T
    columns_name = ['acc_x', 'acc_y', 'acc_z',
                    'o_w', 'o_x', 'o_y', 'o_z',
                    'mag_x', 'mag_y', 'mag_z',
                    'gyr_x', 'gyr_y', 'gyr_z',
                    'gra_x', 'gra_y', 'gra_z', 'lacc_x',
                    'lacc_y', 'lacc_z']
    df = pd.DataFrame(matrix, columns=columns_name)
    print("df shape",df.shape)
    return df


def fun1(acc_x, acc_y, acc_z, o_w, o_x, o_y, o_z, m_x, m_y, m_z, gy_x, gy_y, gy_z, g_x, g_y, g_z, l_x, l_y, l_z):
    # print(len(acc_x))
    # print(len(m_x))
    # print(len(gy_x))
    # print(len(o_w))
    # print(len(g_x))
    # print(len(l_x))
    df = change(acc_x, acc_y, acc_z, o_w, o_x, o_y, o_z, m_x, m_y, m_z, gy_x, gy_y, gy_z, g_x, g_y, g_z, l_x, l_y, l_z)
    df = rotate(df)
    df = feature_calculator(df)
    print(len(df))
    col = [1364, 543, 1361, 1348, 219, 1375, 1349, 1366, 1363, 294, 1347]
    ans = []
    for i in col:
        ans.append(float(df[i]))

    return ans
