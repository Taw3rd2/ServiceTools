package com.waldorfprogramming.servicetools3.customers.job_history.job_history_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.waldorfprogramming.servicetools3.daily_service.DispatchModel
import com.waldorfprogramming.servicetools3.utilities.javaDateToString

@Composable
fun CustomerJobHistoryListItem(
    dispatch: DispatchModel,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 5.dp,
        modifier = Modifier
            .padding(5.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(color = MaterialTheme.colors.background)
        ) {
            Row {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentWidth(Alignment.Start),
                    text = dispatch.issue,
                    fontSize = 22.sp,
                    color = MaterialTheme.colors.onSurface
                )
            }
            Row {
                if(dispatch.techLead.isNotEmpty()){
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentWidth(Alignment.Start),
                        text = dispatch.techLead,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colors.primary
                    )
                }
                if(dispatch.timeAlotted.isNotEmpty()){
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentWidth(Alignment.End),
                        text = javaDateToString(dispatch.dateScheduled),
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colors.primary
                    )
                }
            }
        }
    }
}