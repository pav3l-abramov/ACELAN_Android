package com.example.acelanandroid.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.acelanandroid.R
import java.util.Collections.rotate

@Composable
fun FABMaterialComposable(
    mainButtonOn: Boolean,
    onCancelMain: () -> Unit,
    onCancelFilter: () -> Unit,
    onCancelGraph: () -> Unit,
    color: Color
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End) {
        if (mainButtonOn) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.background(color, shape = RoundedCornerShape(20.dp))
                    .padding(4.dp).width(150.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                        .clickable { onCancelGraph() }) {
                    Text("Graph")
                    FloatingActionButton(
                        shape = CircleShape,
                        onClick = { onCancelGraph() },
                        content = {
                            Icon(
                                painter = painterResource(R.drawable.baseline_auto_graph_24),
                                contentDescription = null
                            )

                        },
                        modifier = Modifier
                            .size(55.dp)
                            .padding(5.dp)


                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                        .clickable { onCancelFilter() }) {
                    Text("Filter")
                    FloatingActionButton(
                        shape = CircleShape,
                        onClick = { onCancelFilter() },
                        content = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(R.drawable.baseline_filter_list_24),
                                    contentDescription = null,
                                )
                            }

                        },
                        modifier = Modifier
                            .size(55.dp)
                            .padding(5.dp)

                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
        }

        FloatingActionButton(
            shape = CircleShape,
            onClick = { onCancelMain() },
            content = {
                Row(verticalAlignment = Alignment.CenterVertically){
                Icon(
                    painter = painterResource(if (mainButtonOn) R.drawable.baseline_arrow_drop_down_24 else R.drawable.baseline_arrow_drop_up_24),
                    contentDescription = null,
                    modifier=Modifier.fillMaxSize()
                )
                }

            },
            modifier= Modifier
                .size(55.dp)

        )
    }

}

@Composable
fun FABTaskComposable(
    onCancelFilter: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End
    ) {
        FloatingActionButton(
            shape = CircleShape,
            onClick = { onCancelFilter() },
            content = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_filter_list_24),
                        contentDescription = null
                    )
                }

            },
            modifier = Modifier
                .size(55.dp)

        )
    }
}

@Composable
fun FABOpenMaterialComposable(
    mainButtonOn: Boolean,
    onCancelMain: () -> Unit,
    onDelete:()->Unit,
    color: Color
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End
    ) {
        if (mainButtonOn) {
                FloatingActionButton(
                    shape = CircleShape,
                    onClick = { onDelete() },
                    content = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_delete_outline_24),
                                contentDescription = null
                            )
                        }

                    },
                    modifier = Modifier
                        .size(55.dp)

                )
            Spacer(modifier = Modifier.height(5.dp))

                FloatingActionButton(
                    shape = CircleShape,
                    onClick = { onCancelMain() },
                    content = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_auto_graph_24 ),
                                contentDescription = null
                            )
                        }

                    },
                    modifier = Modifier
                        .size(55.dp)

                )

        }
        else{
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.background(color, shape = RoundedCornerShape(20.dp))
                    .width(170.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                        .clickable { onCancelMain() }) {
                    Text("Add To Graph")
                    FloatingActionButton(
                        shape = CircleShape,
                        onClick = { onCancelMain() },
                        content = {
                            Icon(
                                painter = painterResource(R.drawable.baseline_add_circle_outline_24),
                                contentDescription = null
                            )

                        },
                        modifier = Modifier
                            .size(55.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun FABTaskDrawComposable(
    isDraw:Boolean,
    isGraph:Boolean,
    drawModel: () -> Unit,
    drawGraph: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.End
    ) {
        if(isDraw) {
            FloatingActionButton(
                shape = CircleShape,
                onClick = { drawModel() },
                content = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_3d_rotation_24),
                            contentDescription = null
                        )
                    }

                },
                modifier = Modifier
                    .size(55.dp)

            )
        }

        if(isGraph) {
            Spacer(modifier = Modifier.height(5.dp))
            FloatingActionButton(
                shape = CircleShape,
                onClick = { drawGraph() },
                content = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_auto_graph_24),
                            contentDescription = null
                        )
                    }

                },
                modifier = Modifier
                    .size(55.dp)

            )
        }
    }
}
