package com.example.rrgbs.ui.screens.store

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rrgbs.model.CartItem
import com.example.rrgbs.model.StoreProduct
import com.example.rrgbs.ui.theme.CrimsonRed
import com.example.rrgbs.ui.theme.DeepNavy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartBottomSheet(
    cart: List<CartItem>,
    onUpdateQty: (StoreProduct, Int) -> Unit,
    onClearCart: () -> Unit,
    onCheckout: () -> Unit,
    onDismiss: () -> Unit
) {
    val totalAmount = cart.sumOf { it.product.price * it.qty }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Business Cart & Quotation",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = DeepNavy
                    )
                    Text(
                        text = "${cart.sumOf { it.qty }} Items selected",
                        fontSize = 12.sp,
                        color = Color(0xFF64748B)
                    )
                }

                if (cart.isNotEmpty()) {
                    TextButton(onClick = onClearCart) {
                        Text("Clear All", color = Color(0xFFDC2626), fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            if (cart.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.RemoveShoppingCart,
                            contentDescription = null,
                            tint = Color(0xFF94A3B8),
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("Your store cart is empty", fontWeight = FontWeight.Bold, color = DeepNavy)
                        Text("Add housekeeping, uniforms or joining kits from store catalog", fontSize = 12.sp, color = Color(0xFF64748B))
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, fill = false)
                        .heightIn(max = 300.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(cart) { item ->
                        Card(
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = item.product.icon, fontSize = 24.sp)
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = item.product.name,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = DeepNavy,
                                        maxLines = 1
                                    )
                                    Text(
                                        text = "₹${item.product.price} each • Total: ₹${item.product.price * item.qty}",
                                        fontSize = 11.sp,
                                        color = CrimsonRed,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }

                                // Qty Controls
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    IconButton(
                                        onClick = { onUpdateQty(item.product, item.qty - 1) },
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(Color.White)
                                    ) {
                                        Icon(imageVector = Icons.Default.Remove, contentDescription = "Decrease", modifier = Modifier.size(14.dp))
                                    }

                                    Text(
                                        text = "${item.qty}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = DeepNavy
                                    )

                                    IconButton(
                                        onClick = { onUpdateQty(item.product, item.qty + 1) },
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(CrimsonRed)
                                    ) {
                                        Icon(imageVector = Icons.Default.Add, contentDescription = "Increase", tint = Color.White, modifier = Modifier.size(14.dp))
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Total calculation
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Estimated Order Total:", fontSize = 14.sp, color = Color(0xFF64748B))
                    Text(
                        text = "₹$totalAmount",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = CrimsonRed
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onCheckout,
                    colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(imageVector = Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Proceed to Quotation & Order", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
