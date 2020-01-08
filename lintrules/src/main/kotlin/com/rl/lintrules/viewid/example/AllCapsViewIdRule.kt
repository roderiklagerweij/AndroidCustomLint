package com.rl.lintrules.viewid.example

import com.rl.lintrules.viewid.ViewIdRule

class AllCapsViewIdRule : ViewIdRule {

    override fun isValidId(id: String, viewType: String, layoutName: String) =
        id.toUpperCase() == id


    override fun getMessage() =
        "Your view id's need to be all capital"
}