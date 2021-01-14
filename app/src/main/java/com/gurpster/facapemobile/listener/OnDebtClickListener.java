package com.gurpster.facapemobile.listener;

import com.gurpster.facapemobile.data.entity.Debt;

/**
 * Created by Sistemas on 08/12/2017.
 */

public interface OnDebtClickListener {

    void onItemClick(Debt debt);

    void onLongItemClick(Debt debt);

    void onDownloadClick(Debt debt);
}
